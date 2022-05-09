package com.umc.footprint.src.users;

import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponseStatus;
import com.umc.footprint.config.EncryptProperties;
import com.umc.footprint.src.AwsS3Service;
import com.umc.footprint.src.model.User;
import com.umc.footprint.src.repository.TagRepository;
import com.umc.footprint.src.repository.UserRepository;
import com.umc.footprint.src.repository.WalkRepository;
import com.umc.footprint.src.users.model.*;
import com.umc.footprint.utils.AES128;
import com.umc.footprint.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

import static com.umc.footprint.config.BaseResponseStatus.*;

@Slf4j
@Service
public class UserService {
    private final UserDao userDao;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final JwtService jwtService;
    private final AwsS3Service awsS3Service;
    private final EncryptProperties encryptProperties;

    @Autowired
    public UserService(UserDao userDao, UserRepository userRepository, TagRepository tagRepository, JwtService jwtService, AwsS3Service awsS3Service, EncryptProperties encryptProperties) {
        this.userDao = userDao;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.jwtService = jwtService;
        this.awsS3Service = awsS3Service;
        this.encryptProperties = encryptProperties;
    }

    // 해당 유저의 산책기록 중 태그를 포함하는 산책기록 조회
    public List<GetTagRes> getTagResult(String userId, String tag) throws BaseException {
        try {
            Integer userIdx = userRepository.findByUserId(userId).getUserIdx();

            tag = "#" + tag;
            // 1. 태그 검색을 위한 키워드 암호화
            String encryptedTag = new AES128(encryptProperties.getKey()).encrypt(tag);

            // 2. 태그 검색
            List<WalkHashtag> walkAndHashtagList = tagRepository.findAllWalkAndHashtag(encryptedTag, userIdx);

            // 3. 추출한 값으로 response 객체 초기화
            Set<String> dateSet = new HashSet<>();
            // 날짜만 추출
            for (WalkHashtag walkHashtag : walkAndHashtagList) {
                dateSet.add(
                        // 날짜 + 요일 ex) 2022. 5. 8 일
                        walkHashtag.getEndAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")).replace(".0", ". ")
                        + " " + walkHashtag.getEndAt().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN)
                );
            }

            // response 객체
            List<GetTagRes> getTagResList = new ArrayList<>();
            // response 객체에 저장된 walkIdx (중복 저장 확인용)
            List<Integer> responseWalkIdxList = new ArrayList<>();

            // 날짜에 따라 분류
            for (String date : dateSet) {
                // walk 정보 + hashtag 리스트
                Set<SearchWalk> searchWalkSet = new HashSet<>();

                for (WalkHashtag walkHashtag : walkAndHashtagList) {
                    // 날짜 + 요일 ex) 2022. 5. 8 일
                    String walkDate = walkHashtag.getEndAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")).replace(".0", ". ")
                            + " " + walkHashtag.getEndAt().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
                    // 날짜 같은 경우만 response 객체에 삽입 && response에 중복된 산책이 없어야함
                    if (date.equals(walkDate) && responseWalkIdxList.indexOf(walkHashtag.getWalkIdx()) == -1) {
                        // hashtag 리스트
                        List<String> hashtagList = new ArrayList<>();
                        for (WalkHashtag hashtag : walkAndHashtagList) {
                            // 동일한 산책에 있는 지 확인
                            if (walkHashtag.getWalkIdx().equals(hashtag.getWalkIdx())) {
                                hashtagList.add(new AES128(encryptProperties.getKey()).decrypt(hashtag.getHashtag()));
                            }
                        }


                        searchWalkSet.add(
                                SearchWalk.builder()
                                        // 산책 정보
                                        .userDateWalk(
                                                UserDateWalk.builder()
                                                        .walkIdx(walkHashtag.getWalkIdx())
                                                        .startTime(walkHashtag.getStartAt().format(DateTimeFormatter.ofPattern("HH:mm")))
                                                        .endTime(walkHashtag.getEndAt().format(DateTimeFormatter.ofPattern("HH:mm")))
                                                        .pathImageUrl(new AES128(encryptProperties.getKey()).decrypt(walkHashtag.getPathImageUrl()))
                                                        .build()
                                        )
                                        .hashtag(hashtagList)
                                        .build()
                        );
                        responseWalkIdxList.add(walkHashtag.getWalkIdx());
                    }
                }

                getTagResList.add(
                        GetTagRes.builder()
                                .walkAt(date)
                                .walks(new ArrayList<>(searchWalkSet))
                                .build()
                );
            }

            return getTagResList;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // yummy 12
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public BadgeInfo modifyRepBadge(int userIdx, int badgeIdx) throws BaseException {
        try {
            // 해당 뱃지가 Badge 테이블에 존재하는 뱃지인지?
            if(!userDao.badgeCheck(badgeIdx)) {
                throw new BaseException(INVALID_BADGEIDX);
            }

            // 유저가 해당 뱃지를 갖고 있고, ACTIVE 뱃지인지?
            if(!userDao.userBadgeCheck(userIdx, badgeIdx)) {
                throw new BaseException(NOT_EXIST_USER_BADGE);
            }

            BadgeInfo patchRepBadgeInfo = userDao.modifyRepBadge(userIdx, badgeIdx);
            return patchRepBadgeInfo;
          } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 유저 정보 수정(Patch)
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void modifyUserInfo(int userIdx, PatchUserInfoReq patchUserInfoReq) throws BaseException {
        try {
            int result = userDao.modifyUserInfo(userIdx, patchUserInfoReq);

            if (result == 0) { // 유저 정보 변경 실패
                throw new BaseException(MODIFY_USERINFO_FAIL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void modifyGoal(int userIdx, PatchUserGoalReq patchUserGoalReq) throws BaseException{
        try{
            int resultTime = userDao.modifyUserGoalTime(userIdx, patchUserGoalReq);
            if(resultTime == 0)
                throw new BaseException(BaseResponseStatus.MODIFY_USER_GOAL_FAIL);

            // 요일별 인덱스 차이 해결을 위한 임시 코드
            List<Integer> dayIdxList = new ArrayList<>();
            for (Integer dayIdx: patchUserGoalReq.getDayIdx()){
                if(dayIdx == 7)
                    dayIdxList.add(1);
                else
                    dayIdxList.add(dayIdx+1);
            }
            Collections.sort(dayIdxList);
            patchUserGoalReq.setDayIdx(dayIdxList);
            log.debug("dayIdxList : {}",dayIdxList);
            //

            int resultDay = userDao.modifyUserGoalDay(userIdx, patchUserGoalReq);
            if(resultDay == 0)
                throw new BaseException(BaseResponseStatus.MODIFY_USER_GOAL_FAIL);

        } catch(Exception exception){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }


    // 해당 userIdx를 갖는 Goal 정보 저장
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public int postUserInfo(int userIdx, PatchUserInfoReq patchUserInfoReq) throws BaseException{
        try {
            int resultInfo = userDao.modifyUserInfo(userIdx, patchUserInfoReq);
            log.debug("resultInfo: {}", resultInfo);

            // 요일별 인덱스 차이 해결을 위한 임시 코드
            List<Integer> dayIdxList = new ArrayList<>();
            for (Integer dayIdx: patchUserInfoReq.getDayIdx()){
                if(dayIdx == 7)
                    dayIdxList.add(1);
                else
                    dayIdxList.add(dayIdx+1);
            }
            Collections.sort(dayIdxList);
            patchUserInfoReq.setDayIdx(dayIdxList);
            log.debug("dayIdxList : {}",dayIdxList);
            //

            int result = userDao.postGoal(userIdx, patchUserInfoReq);
            log.debug("result : {}", result);
            int resultNext = userDao.postGoalNext(userIdx, patchUserInfoReq);
            log.debug("resultNext: {}", resultNext);

            if(resultInfo == 0 || result == 0 || resultNext == 0)
                return 0;
            return 1;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes checkEmail(String email) throws BaseException {
        try {
            log.debug("email: {}", email);
            // checkExistFlag == true -> 유저 이미 존재
            // checkExistFlag == false -> 유저 정보 등록 필요
            boolean checkExistFlag = userRepository.existsByEmail(email);

            log.debug("checkExistFlag: {}", checkExistFlag);

            if (checkExistFlag) {
                // email로 userId랑 상태 추출
                User user = userRepository.findByEmail(email);
                PostLoginRes postLoginRes = PostLoginRes.builder()
                        .jwtId(user.getUserId())
                        .status(user.getStatus())
                        .build();
                // userId 암호화
                String jwtId = jwtService.createJwt(postLoginRes.getJwtId());
                // response에 저장
                postLoginRes.setJwtId(jwtId);
                return postLoginRes;
            } else {
                return PostLoginRes.builder()
                        .jwtId("")
                        .status("NONE")
                        .checkMonthChanged(false)
                        .build();
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public PostLoginRes postUserLogin(PostLoginReq postLoginReq) throws BaseException {
        { // email 중복 확인 있으면 status에 Done 넣고 return
            try {
                String encryptEmail = new AES128(encryptProperties.getKey()).encrypt(postLoginReq.getEmail());
                PostLoginRes result = checkEmail(encryptEmail);
                log.debug("유저의 status: {}", result.getStatus());
                // status: NONE -> 회원가입(유저 정보 db에 등록 필요)
                // status: ACTIVE -> 로그인
                // status: ACTIVE -> 정보 입력 필요
                switch (result.getStatus()) {
                    case "NONE":
                            // 암호화
                            String jwt = jwtService.createJwt(postLoginReq.getUserId());
                            // 유저 정보 db에 등록
                            postLoginReq.setEncryptedInfos(new AES128(encryptProperties.getKey()).encrypt(postLoginReq.getUsername()), encryptEmail);
                            userRepository.save(postLoginReq.toUserEntity());

                            return PostLoginRes.builder()
                                    .jwtId(jwt)
                                    .status("ONGOING")
                                    .checkMonthChanged(false)
                                    .build();
                    case "ACTIVE":
                    case "ONGOING":
                        return result;
                }
                return null;
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        }
    }

    public PostLoginRes modifyUserLogAt(String userId) throws BaseException {
        try {
            // 이전에 로그인 했던 시간
            User user = userRepository.findByUserId(userId);

            LocalDateTime beforeLogAt = user.getLogAt();

            boolean checkMonthChangedFlag = true;

            // 달이 같은 경우
            if (beforeLogAt.getMonth() == LocalDateTime.now().getMonth()) {
                // 달이 바뀌지 않았다고 response에 저장
                checkMonthChangedFlag = false;
            }

            // 현재 로그인하는 시간 logAt에 저장
            user.updateLogAtNow(LocalDateTime.now());
            userRepository.save(user);

            return PostLoginRes.builder()
                    .jwtId(jwtService.createJwt(user.getUserId()))
                    .status(user.getStatus())
                    .checkMonthChanged(checkMonthChangedFlag)
                    .build();
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void deleteUser(int userIdx) throws BaseException {
        try{
            // GoalNext 테이블
            userDao.deleteGoalNext(userIdx);

            // GoalDayNext 테이블
            userDao.deleteGoalDayNext(userIdx);

            // Goal 테이블
            userDao.deleteGoal(userIdx);

            // GoalDay 테이블
            userDao.deleteGoalDay(userIdx);
            // UserBadge 테이블
            userDao.deleteUserBadge(userIdx);

            // Tag 테이블
            userDao.deleteTag(userIdx);

            // Photo 테이블 -> s3에서 이미지 url 먼저 삭제 후 테이블 삭제 필요
            List<String> imageUrlList = userDao.getImageUrlList(userIdx); //S3에서 사진 삭제
            for(String imageUrl : imageUrlList) {
                String decryptedImageUrl = new AES128(encryptProperties.getKey()).decrypt(imageUrl);
                String fileName = decryptedImageUrl.substring(decryptedImageUrl.lastIndexOf("/")+1); // 파일 이름만 자르기
                awsS3Service.deleteFile(fileName);
            }
            userDao.deletePhoto(userIdx); //Photo 테이블에서 삭제

            // Footprint 테이블
            userDao.deleteFootprint(userIdx);

            // Walk 테이블 - 동선 이미지 S3 에서도 삭제
            List<String> pathImageUrlList = userDao.getPathImageUrlList(userIdx); //S3에서 사진 삭제
            for(String imageUrl : pathImageUrlList) {
                String decryptedImageUrl = new AES128(encryptProperties.getKey()).decrypt(imageUrl);
                String fileName = decryptedImageUrl.substring(decryptedImageUrl.lastIndexOf("/") + 1); // 파일 이름만 자르기
                awsS3Service.deleteFile(fileName);
            }
            userDao.deleteWalk(userIdx);

            // User 테이블
            userDao.deleteUser(userIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
