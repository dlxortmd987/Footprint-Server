package com.umc.footprint.src.course;

import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.EncryptProperties;
import com.umc.footprint.src.model.*;
import com.umc.footprint.src.repository.*;
import com.umc.footprint.src.walks.WalkService;
import com.umc.footprint.src.course.model.GetCourseInfoRes;
import com.umc.footprint.src.course.model.GetCourseListReq;
import com.umc.footprint.src.course.model.GetCourseListRes;
import com.umc.footprint.src.walks.model.*;
import com.umc.footprint.utils.AES128;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.umc.footprint.config.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {

    private final WalkService walkService;
    private final HashtagRepository hashtagRepository;
    private final CourseRepository courseRepository;
    private final CourseTagRepository courseTagRepository;
    private final UserCourseRepository userCourseRepository;
    private final MarkRepository markRepository;
    private final WalkRepository walkRepository;
    private final TagRepository tagRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final EncryptProperties encryptProperties;

    /** API.32 사용자 디바이스 지도 좌표안에 존재하는 모든 코스들을 가져온다. */
    public List<GetCourseListRes> getCourseList(GetCourseListReq getCourseListReq, int userIdx){

        // response로 보낼 코스 정보를 저장할 List
        List<GetCourseListRes> courseListResList = new ArrayList<>();

        // 1. DB에 존재하는 모든 코스 정보중에서 디바이스 지도 좌표안에 존재하는 코스 추출
        List<Course> allCourseInDB = courseRepository.findAllByStatus("ACTIVE");

        System.out.println("allCourseInDB.size() = " + allCourseInDB.size());

        for(Course course : allCourseInDB){
            System.out.println("course = " + course);
            double courseLat = course.getStartCoordinate().getX();
            double courseLong = course.getStartCoordinate().getY();

            // 2. DB에 있는 코스들중 보내준 위도 / 경도 사이에 존재하는 코스들만 추출
            if(courseLat < getCourseListReq.getNorth() && courseLat > getCourseListReq.getSouth()
                    && courseLong < getCourseListReq.getEast() && courseLong > getCourseListReq.getWest()){

                // 2-1. 코스 태그 추출
                // CourseTag 테이블에서 hashtagIdx 추출후 HashTag 테이블에서 인덱스에 해당하는 해시태그들 List화
                List<CourseTag> courseTagMappingList = courseTagRepository.findAllByCourse(course);

                List<String> courseTags = new ArrayList<>();
                for(CourseTag courseTag: courseTagMappingList){
                    for (Hashtag hashtag : courseTag.getHashtagList()) {
                        courseTags.add(hashtagRepository.findByHashtagIdx(hashtag.getHashtagIdx()).get().getHashtag());
                    }
                }



                // 2-2. 유저가 해당 코스를 like 했는지 확인
                System.out.println("Check Point 3");
                List<UserCourse> userCourseList = userCourseRepository.findByUserIdx(userIdx);
                boolean userCourseLike = false;
                if(userCourseList.contains(course.getCourseIdx()))
                    userCourseLike = true;

                // 2-3. 해당 코스에 사진이 들어있는지 확인
                // 사진이 없다면 기본 이미지 URL 입력
                String courseImgUrl = course.getCourseImg();
                if(courseImgUrl.isBlank()){
                    courseImgUrl = "https://mystepsbucket.s3.ap-northeast-2.amazonaws.com/BaseImage/%E1%84%8F%E1%85%A9%E1%84%89%E1%85%B3%E1%84%80%E1%85%B5%E1%84%87%E1%85%A9%E1%86%AB%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5.png"; //* 기본 이미지 URL
                }

                // 2-3. courseListResList에 해당 추천 코스 정보 add
                courseListResList.add(GetCourseListRes.builder()
                        .courseIdx(course.getCourseIdx())
                        .startLat(courseLat)
                        .startLong(courseLong)
                        .courseName(course.getCourseName())
                        .courseDist(course.getLength())
                        .courseTime(course.getCourseTime())
                        .courseLike(course.getLikeNum())
                        .courseImg(courseImgUrl)
                        .courseTags(courseTags)
                        .userCourseLike(userCourseLike)
                        .build());
            }
        }

        return courseListResList;
    }

    /** API.34 원하는 코스의 경로 좌표와 상세 설명을 가져온다. */
    public GetCourseInfoRes getCourseInfo(int courseIdx) throws BaseException {

        // 1. courseIdx로 코스 정보 가져오기
        Optional<Course> course = courseRepository.findByCourseIdx(courseIdx);

        // 2. response 생성 후 reutrn(coordinate 복호화)
        return GetCourseInfoRes.builder()
                .coordinate(walkService.convertStringTo2DList(course.get().getCoordinate()))
                .courseDisc(course.get().getDescription())
                .build();

    }

    public String modifyMark(int courseIdx, String userId) throws BaseException {
        // userIdx 추출
        Integer userIdx = getUserIdx(userId);

        // 저장된 코스 불러오기
        Course savedCourse = courseRepository.findById(courseIdx).get();

        Optional<Mark> optionalMark = markRepository.findByCourseIdxAndUserIdx(courseIdx, userIdx);

        if (optionalMark.isPresent()) {
            Mark savedMark = optionalMark.get();

            savedMark.modifyMark();

            Mark modifiedMark = markRepository.save(savedMark);
            if (modifiedMark.getMark()) {
                return "찜하기";
            } else {
                return "찜하기 취소";
            }
        } else {
            Mark newMark = Mark.builder()
                    .courseIdx(courseIdx)
                    .userIdx(userIdx)
                    .mark(true)
                    .build();

            markRepository.save(newMark);

            return "찜하기";
        }
    }

    /**
     *
     * @param courseName String
     * @return 코스 상세 정보들
     * @throws BaseException
     */
    public GetCourseDetailsRes getCourseDetails(String courseName) throws BaseException {

        Course savedCourse = courseRepository.findByCourseNameAndStatus(courseName, "ACTIVE");

        if (savedCourse == null) {
            log.info("코스 이름에 해당하는 코스가 없습니다.");
            throw new BaseException(NOT_EXIST_COURSE);
        }

        Walk savedWalk = walkRepository.findById(savedCourse.getWalkIdx()).get();

        if (savedWalk.getStatus().equals("INACTIVE")) {
            log.info("삭제된 산책입니다.");
            throw new BaseException(DELETED_WALK);
        }

        List<ArrayList<Double>> coordinates;

        // 좌표 변환
        try {
            coordinates = walkService.convertStringTo2DList(new AES128(encryptProperties.getKey()).decrypt(savedCourse.getCoordinate()));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException | InvalidKeyException exception) {
            throw new BaseException(INVALID_ENCRYPT_STRING);
        }

        ArrayList<HashtagVO> hashtags = new ArrayList<>();
        ArrayList<String> photos = new ArrayList<>();
        for (Footprint footprint : savedWalk.getFootprintList()) {
            // 해시태그 불러오기
            List<Tag> savedTags = tagRepository.findAllByFootprintAndStatus(footprint, "ACTIVE");
            for (Tag savedTag : savedTags) {
                hashtags.add(HashtagVO.builder()
                        .hashtagIdx(savedTag.getHashtag().getHashtagIdx())
                        .hashtag(savedTag.getHashtag().getHashtag())
                        .build());
            }

            // 사진 불러오기
            List<Photo> savedPhotos = photoRepository.findAllByFootprintAndStatus(footprint, "ACTIVE");
            for (Photo savedPhoto : savedPhotos) {
                photos.add(savedPhoto.getImageUrl());
            }
        }
        return GetCourseDetailsRes.builder()
                .courseIdx(savedCourse.getCourseIdx())
                .walkIdx(savedWalk.getWalkIdx())
                .startAt(savedWalk.getStartAt())
                .endAt(savedWalk.getEndAt())
                .distance(savedWalk.getDistance())
                .coordinates(coordinates)
                .hashtags(hashtags)
                .photos(photos)
                .build();
    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public String postCourseDetails(PostCourseDetailsReq postCourseDetailsReq) throws BaseException {

        String encryptedCoordinates;

        // 좌표 암호화
        try {
            encryptedCoordinates = new AES128(encryptProperties.getKey()).encrypt(walkService.convert2DListToString(postCourseDetailsReq.getCoordinates()));
        } catch (Exception exception) {
            log.info("좌표 암호화 실패");
            throw new BaseException(ENCRYPT_FAIL);
        }

        // 코스 이름 중복 확인
        if (courseRepository.existsByCourseNameAndStatus(postCourseDetailsReq.getCourseName(), "ACTIVE")) {
            log.info("코스 이름 중복");
            throw new BaseException(DUPLICATED_COURSE_NAME);
        }

        // Course Entity 에 저장
        Course savedCourse = courseRepository.save(Course.builder()
                .courseName(postCourseDetailsReq.getCourseName())
                .courseImg(postCourseDetailsReq.getCourseImg())
                .startCoordinate(extractStartCoordinate(postCourseDetailsReq.getCoordinates()))
                .coordinate(encryptedCoordinates)
                .address(postCourseDetailsReq.getAddress())
                .length(postCourseDetailsReq.getLength())
                .courseTime(postCourseDetailsReq.getCourseTime())
                .description(postCourseDetailsReq.getDescription())
                .likeNum(0)
                .status("ACTIVE")
                .build());

        if (savedCourse == null) {
            log.info("Course 저장 실패");
            throw new BaseException(DATABASE_ERROR);
        }

        // CourseTag Entity 에 저장
        CourseTag courseTag = CourseTag.builder()
                .course(savedCourse)
                .status("ACTIVE")
                .build();
        for (HashtagVO hashtagVO : postCourseDetailsReq.getHashtags()) {
            courseTag.addHashtag(
                    Hashtag.builder()
                            .hashtagIdx(hashtagVO.getHashtagIdx())
                            .hashtag(hashtagVO.getHashtag())
                            .build()
            );
        }
        CourseTag savedCourseTag = courseTagRepository.save(courseTag);

        if (savedCourseTag == null) {
            log.info("CourseTag 저장 실패");
            throw new BaseException(DATABASE_ERROR);
        }

        return "코스가 등록되었습니다.";
    }

    public Point extractStartCoordinate(List<List<Double>> coordinates) throws BaseException {
        List<Double> firstSection = coordinates.get(0);

        if (firstSection == null) {
            log.info("잘못된 좌표입니다.");
            throw new BaseException(INVALID_COORDINATES);
        }

        StringBuilder st = new StringBuilder();
        st.append("Point")
                .append(" (").append(firstSection.get(0)).append(" ").append(firstSection.get(1)).append(")");
        return (Point) wktToGeometry(st.toString());
    }

    public Geometry wktToGeometry(String wellKnownText) throws BaseException {
        try {
            return new WKTReader().read(wellKnownText);
        } catch (Exception exception) {
            log.info("잘못된 well known text 입니다.");
            throw new BaseException(MODIFY_WKT_FAIL);
        }
    }

    public String modifyCourseLike(Integer courseIdx, String userId, Integer evaluate) throws BaseException {
        Integer userIdx = getUserIdx(userId);

        Optional<Course> OptionalCourse = courseRepository.findById(courseIdx);

        if (OptionalCourse.isEmpty()) {
            throw new BaseException(NOT_EXIST_COURSE);
        }

        Optional<UserCourse> byCourseIdxAndUserIdx = userCourseRepository.findByCourseIdxAndUserIdx(courseIdx, userIdx);

        UserCourse savedUserCourse;
        Course savedCourse = OptionalCourse.get();

        // UserCourse에 저장
        if (byCourseIdxAndUserIdx.isPresent()) {
            UserCourse rawUserCourse = byCourseIdxAndUserIdx.get();

            UserCourse modifiedUserCourse = UserCourse.builder()
                    .userCourseIdx(rawUserCourse.getUserCourseIdx())
                    .userIdx(rawUserCourse.getUserIdx())
                    .courseIdx(rawUserCourse.getCourseIdx())
                    .walkIdx(rawUserCourse.getWalkIdx())
                    .courseCount(rawUserCourse.getCourseCount() + 1L)
                    .build();

            savedUserCourse = userCourseRepository.save(modifiedUserCourse);
        } else {
            savedUserCourse = userCourseRepository.save(
                    UserCourse.builder()
                            .userIdx(userIdx)
                            .courseIdx(courseIdx)
                            .walkIdx(savedCourse.getWalkIdx())
                            .courseCount(1L)
                            .build()
            );
        }

        if (evaluate == 1) { // 좋았어요 눌렀을 때
            // likeNum 1 증가
            savedCourse.addLikeNum();

            // Course 저장
            courseRepository.save(savedCourse);
        }

        if (savedUserCourse == null) {
            log.info("UserCourse 저장 실패");
            throw new BaseException(DATABASE_ERROR);
        }

        return "좋아요";
    }

    public String modifyCourseDetails(PatchCourseDetailsReq patchCourseDetailsReq, String userId) throws BaseException {
        Integer userIdx = getUserIdx(userId);

        Course savedCourse = courseRepository.findById(patchCourseDetailsReq.getCourseIdx()).get();

        if (savedCourse == null) {
            throw new BaseException(NOT_EXIST_COURSE);
        }

        String encryptedCoordinates;

        // 좌표 암호화
        try {
            encryptedCoordinates = new AES128(encryptProperties.getKey()).encrypt(walkService.convert2DListToString(patchCourseDetailsReq.getCoordinates()));
        } catch (Exception exception) {
            log.info("좌표 암호화 실패");
            throw new BaseException(ENCRYPT_FAIL);
        }

        // 코스 이름 변경 시 중복 확인
        if (savedCourse.getCourseName().equals(patchCourseDetailsReq.getCourseName())) {
            if (courseRepository.existsByCourseNameAndStatus(patchCourseDetailsReq.getCourseName(), "ACTIVE")) {
                log.info("코스 이름 중복");
                throw new BaseException(DUPLICATED_COURSE_NAME);
            }
        }

        // Course Entity 에 저장
        Point startCoordinate = extractStartCoordinate(patchCourseDetailsReq.getCoordinates());
        log.info("start coordinate: {}", startCoordinate.toString());
        Course beforeSavedCourse = Course.builder()
                .courseIdx(savedCourse.getCourseIdx())
                .courseName(patchCourseDetailsReq.getCourseName())
                .courseImg(patchCourseDetailsReq.getCourseImg())
                .startCoordinate(startCoordinate)
                .coordinate(encryptedCoordinates)
                .address(patchCourseDetailsReq.getAddress())
                .length(patchCourseDetailsReq.getLength())
                .courseTime(patchCourseDetailsReq.getCourseTime())
                .description(patchCourseDetailsReq.getDescription())
                .likeNum(0)
                .status("ACTIVE")
                .build();
        Course modifiedCourse = courseRepository.save(beforeSavedCourse);

        if (modifiedCourse == null) {
            log.info("수정된 코스 저장 실패");
            throw new BaseException(DATABASE_ERROR);
        }

        // 해당 코스 태그 entity 불러오기
        CourseTag savedCourseTag = courseTagRepository.findByCourseAndStatus(modifiedCourse, "ACTIVE");

        // CourseTag Entity 에 저장
        CourseTag beforeSavedCourseTag = CourseTag.builder()
                .courseTagIdx(savedCourseTag.getCourseTagIdx())
                .course(modifiedCourse)
                .status("ACTIVE")
                .build();

        // 코스, 해시 태그 매핑
        for (HashtagVO hashtagVO : patchCourseDetailsReq.getHashtags()) {
            beforeSavedCourseTag.addHashtag(
                    Hashtag.builder()
                            .hashtagIdx(hashtagVO.getHashtagIdx())
                            .hashtag(hashtagVO.getHashtag())
                            .build()
            );
        }
        CourseTag modifiedCourseTag = courseTagRepository.save(beforeSavedCourseTag);

        if (modifiedCourseTag == null) {
            log.info("수정된 코스 태그 저장 실패");
            throw new BaseException(DATABASE_ERROR);
        }

        return "코스가 수정되었습니다.";
    }

    public Integer getUserIdx(String userId) throws BaseException {
        Integer userIdx = userRepository.findByUserId(userId).getUserIdx();

        if (userIdx == null) {
            throw new BaseException(NOT_EXIST_USER);
        }

        return userIdx;
    }

    public GetWalkDetailsRes getWalkDetails(Integer walkNumber, String userId) throws BaseException {
//        Course savedCourse = courseRepository.findByCourseNameAndStatus(courseName, "ACTIVE");

        Integer userIdx = getUserIdx(userId);

        Walk savedWalk = walkService.getWalkByNumber(walkNumber, userIdx);

        if (savedWalk == null) {
            log.info("{} 번째 산책을 찾을 수 없습니다.", walkNumber);
            throw new BaseException(NOT_EXIST_WALK);
        }

        List<ArrayList<Double>> coordinates;

        // 좌표 변환
        try {
            coordinates = walkService.convertStringTo2DList(new AES128(encryptProperties.getKey()).decrypt(savedWalk.getCoordinate()));
        } catch (Exception exception) {
            throw new BaseException(INVALID_ENCRYPT_STRING);
        }

        ArrayList<HashtagVO> hashtags = new ArrayList<>();
        ArrayList<String> photos = new ArrayList<>();
        for (Footprint footprint : savedWalk.getFootprintList()) {
            // 해시태그 불러오기
            List<Tag> savedTags = tagRepository.findAllByFootprintAndStatus(footprint, "ACTIVE");
            for (Tag savedTag : savedTags) {
                hashtags.add(HashtagVO.builder()
                        .hashtagIdx(savedTag.getHashtag().getHashtagIdx())
                        .hashtag(savedTag.getHashtag().getHashtag())
                        .build());
            }

            // 사진 불러오기
            List<Photo> savedPhotos = photoRepository.findAllByFootprintAndStatus(footprint, "ACTIVE");
            for (Photo savedPhoto : savedPhotos) {
                photos.add(savedPhoto.getImageUrl());
            }
        }
        return GetWalkDetailsRes.builder()
                .walkIdx(savedWalk.getWalkIdx())
                .startAt(savedWalk.getStartAt())
                .endAt(savedWalk.getEndAt())
                .distance(savedWalk.getDistance())
                .coordinates(coordinates)
                .hashtags(hashtags)
                .photos(photos)
                .build();
    }
}
