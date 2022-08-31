package com.umc.footprint.src.users;

import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.EncryptProperties;
import com.umc.footprint.src.goal.model.dto.GetUserGoalRes;
import com.umc.footprint.src.walks.WalkDao;

import com.umc.footprint.src.users.model.GetUserTodayRes;
import com.umc.footprint.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umc.footprint.src.users.model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.umc.footprint.config.BaseResponseStatus.*;

@Slf4j
@Service
public class UserProvider {

    private final WalkDao walkDao;
    private final UserDao userDao;
    private final EncryptProperties encryptProperties;
    private final JwtService jwtService;

    @Autowired
    public UserProvider(WalkDao walkDao, UserDao userDao, EncryptProperties encryptProperties, JwtService jwtService) {
        this.walkDao = walkDao;
        this.userDao = userDao;
        this.encryptProperties = encryptProperties;
        this.jwtService = jwtService;
    }

    // 해당 userIdx를 갖는 오늘 산책 관련 정보 조회
    public GetUserTodayRes getUserToday(int userIdx) throws BaseException {

        System.out.println("Check Point Prov.1");
        GetUserTodayRes userTodayRes = userDao.getUserToday(userIdx);
        System.out.println("Check Point Prov.2");

        System.out.println("userTodayRes.getWalkTime() = " + userTodayRes.getWalkTime());
        System.out.println("userTodayRes.getCalorie() = " + userTodayRes.getCalorie());
        System.out.println("userTodayRes.getDistance() = " + userTodayRes.getDistance());
        System.out.println("userTodayRes.getWalkGoalTime() = " + userTodayRes.getWalkGoalTime());

        log.debug("userTodayRes: {}", userTodayRes);

        return userTodayRes;
    }

    // 해당 userIdx를 갖는 date의 산책 관련 정보 조회
    public List<GetUserDateRes> getUserDate(int userIdx, String date) throws BaseException {

        try {
            // Validation 2. Walk Table 안 존재하는 User인지 확인
            boolean existUserResult = userDao.checkUser(userIdx,"Walk");
            if (existUserResult == false) {
                List<GetUserDateRes> returnList = new ArrayList<>();
                return returnList;
            }

            // Validation 3. 해당 날짜에 User가 기록한 Walk가 있는지 확인
            int existUserDateResult = userDao.checkUserDateWalk(userIdx, date);
            if (existUserDateResult == 0) {
                List<GetUserDateRes> returnList = new ArrayList<>();
                return returnList;
            }

            List<GetUserDateRes> userDateRes = userDao.getUserDate(userIdx, date);


            return userDateRes;
        } catch(Exception exception){

            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 userIdx를 갖는 "이번달" Goal 정보 조회
    public GetUserGoalRes getUserGoal(int userIdx) throws BaseException{
        try{
            GetUserGoalRes getUserGoalRes = userDao.getUserGoal(userIdx);

            // 요일별 인덱스 차이 해결을 위한 임시 코드
            List<Integer> dayIdxList = new ArrayList<>();
            for (Integer dayIdx: getUserGoalRes.getDayIdx()){
                if(dayIdx == 1)
                    dayIdxList.add(7);
                else
                    dayIdxList.add(dayIdx-1);
            }
            Collections.sort(dayIdxList);
            getUserGoalRes.setDayIdx(dayIdxList);
            //

            return getUserGoalRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 userIdx를 갖는 "다음달" Goal 정보 조회
    public GetUserGoalRes getUserGoalNext(int userIdx) throws BaseException{
        try{
            GetUserGoalRes getUserGoalRes = userDao.getUserGoalNext(userIdx);

            // 요일별 인덱스 차이 해결을 위한 임시 코드
            List<Integer> dayIdxList = new ArrayList<>();
            for (Integer dayIdx: getUserGoalRes.getDayIdx()){
                if(dayIdx == 1)
                    dayIdxList.add(7);
                else
                    dayIdxList.add(dayIdx-1);
            }
            Collections.sort(dayIdxList);
            getUserGoalRes.setDayIdx(dayIdxList);
            //

            return getUserGoalRes;
        } catch (Exception exception){
          throw new BaseException(DATABASE_ERROR);
        }
    }
  
    // 해당 userIdx를 갖는 User의 세부 정보 조회
    public GetUserInfoRes getUserInfo(int userIdx) throws BaseException {
        try {
            // 1. user 달성률 정보
            UserInfoAchieve userInfoAchieve = userDao.getUserInfoAchieve(userIdx);
            // 2. user 이번달 목표 정보
            GetUserGoalRes getUserGoalRes = userDao.getUserGoal(userIdx);
            // 3. user 통계 정보
            UserInfoStat userInfoStat = userDao.getUserInfoStat(userIdx);

            // 4. 1+2+3
            return new GetUserInfoRes(userInfoAchieve,getUserGoalRes,userInfoStat);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    //yummy 13
//    // 이번달 사용자가 얻은 뱃지 조회 (PRO, LOVER, MASTER)
//    public BadgeInfo getMonthlyBadgeStatus(int userIdx) throws BaseException {
//        try {
//            // 이전달 목표 설정 여부 확인
//            if(!userDao.checkPrevGoalDay(userIdx)) {
//                throw new BaseException(NOT_EXIST_USER_IN_PREV_GOAL);
//            }
//
//            BadgeInfo getBadgeInfo = userDao.getMonthlyBadgeStatus(userIdx);
//            if(getBadgeInfo==null) {
//                throw new BaseException(NO_MONTHLY_BADGE);
//           }
//            return getBadgeInfo;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    // userId로 userIdx 추출
    public int getUserIdx(String userId) throws BaseException {
        try {
            return userDao.getUserIdx(userId);
        } catch (Exception exception) {
            throw new BaseException(NOT_EXIST_USER);
        }
    }

}
