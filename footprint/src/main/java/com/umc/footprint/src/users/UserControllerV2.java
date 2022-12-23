package com.umc.footprint.src.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponse;
import com.umc.footprint.config.BaseResponseStatus;
import com.umc.footprint.src.badge.BadgeService;
import com.umc.footprint.src.badge.model.BadgeDateInfo;
import com.umc.footprint.src.footprints.model.dto.GetFootprintCount;
import com.umc.footprint.src.goal.GoalService;
import com.umc.footprint.src.goal.model.dto.GetUserGoalRes;
import com.umc.footprint.src.goal.model.dto.PatchUserGoalReq;
import com.umc.footprint.src.users.model.GetUserBadges;
import com.umc.footprint.src.users.model.dto.*;
import com.umc.footprint.src.users.model.vo.UserInfoAchieve;
import com.umc.footprint.src.users.model.vo.UserInfoStat;
import com.umc.footprint.utils.JwtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.umc.footprint.config.BaseResponseStatus.*;
import static com.umc.footprint.utils.ValidationRegax.isRegexEmail;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/users")
public class UserControllerV2 {
    private final UserService userService;
    private final GoalService goalService;
    private final JwtService jwtService;
    private final BadgeService badgeService;

    /**
     * 16 API
     * 유저 로그인 API
     * [POST] /users/auth/login
     * @return PostLoginRes
     * @throws JsonProcessingException
     */
    @ResponseBody
    @PostMapping("/auth/login")
    @ApiOperation(value = "로그인 및 회원가입", notes = "기존 회원은 로그인, 신규 회원은 회원 가입을 진행 (판별 기준은 이메일)")
    @ApiImplicitParam(name = "postLoginReq", value = "로그인 정보", required = true)
    public BaseResponse<PostLoginRes> postUser(@RequestBody PostLoginReq postLoginReq) throws JsonProcessingException {

        // 유저 id를 입력하지 않은 경우
        if (postLoginReq.getUserId().isEmpty()) {
            return new BaseResponse<>(POST_USERS_EMPTY_USERID);
        }

        // 이메일을 입력하지 않은 경우
        if (postLoginReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현: 입력받은 이메일이 email@domain.xxx와 같은 형식인지 검사합니다. 형식이 올바르지 않다면 에러 메시지를 보냅니다.
        if (!isRegexEmail(postLoginReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        try {
            // 사용자 등록 또는 로그인
            PostLoginRes postLoginRes = userService.postUserLogin(postLoginReq);

            // 사용자의 로그인한 날짜 이전 기록과 비교 후 달 바뀌면 true return
            postLoginRes.setCheckMonthChanged(userService.modifyUserLogAt(postLoginReq.getUserId()).isCheckMonthChanged());

            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 17 API
     * 유저 자동 로그인 API
     * [GET] /users/autologin
     */
    @ResponseBody
    @GetMapping("/autologin")
    @ApiOperation(value = "자동 로그인", notes = "JWT 토큰을 판별하여 자동 로그인 진행")
    public BaseResponse<PostLoginRes> getCheckMonthChanged() {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            jwtService.getJwt();
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);

            PostLoginRes postLoginRes = userService.modifyUserLogAt(userId);

            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    /**
     * 유저 오늘 산책관련 정보 조회 API
     * [GET] /users/today
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/today")
    @ApiOperation(value = "일별 정보 조회", notes = "오늘의 산책 달성률, 산책 시간, 거리, 칼로리, 오늘 목표")
    public BaseResponse<GetUserTodayRes> getToday(){
        try{
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);

            GetUserTodayRes userTodayRes = userService.getUserToday(userId);

            return new BaseResponse<>(userTodayRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 날짜별 산책관련 정보 조회 API
     * [GET] /users/:date
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{date}")
    @ApiOperation(value = "유저 날짜별 산책관련 정보 조회", notes = "해당 날짜의 발자국 인덱스,  산책시간,  태그,  일별 발자국 횟수, 동선 이미지")
    public BaseResponse<List<GetUserDateRes>> getDateWalk(@PathVariable("date") String date){

        // Validation 1. 날짜 형식 검사
        if(!date.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")){
            return new BaseResponse<>(new BaseException(BaseResponseStatus.INVALID_DATE).getStatus());
        }

        try{
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);

            List<GetUserDateRes> userDateRes = userService.getUserDate(userId, date);

            return new BaseResponse<>(userDateRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 정보 조회 API
     * [GET] /users
     */
    // Path-variable
    @ResponseBody
    @GetMapping("")
    @ApiOperation(value = "유저 정보 조회", notes = "해당 유저 관련 모든 정보")
    public BaseResponse<GetUserRes> getUser() {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);

            GetUserRes getUserRes = userService.getUser(userId);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 유저 정보 변경 API
     * [PATCH] /users/infos/after
     */
    @ResponseBody
    @PatchMapping("/infos/after")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 기본 정보 수정")
    public BaseResponse<String> modifyUserInfo(@RequestBody PatchUserInfoReq patchUserInfoReq) throws JsonProcessingException {

        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);

            if (patchUserInfoReq.getNickname().length() > 8) { // 닉네임 8자 초과
                throw new BaseException(BaseResponseStatus.MAX_NICKNAME_LENGTH);
            }
            if (patchUserInfoReq.getBirth().equals("0000-00-00")) {
                throw new BaseException(BaseResponseStatus.INVALID_BIRTH);
            }

            userService.modifyUserInfoJPA(userId, patchUserInfoReq);

            String result = "유저 정보가 수정되었습니다.";

            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 "이번달" 목표 조회 API
     * [GET] /users/goals
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/goals")
    @ApiOperation(value = "유저 이번달 목표 조회 API", notes = "목표 요일, 시간, 시간대, 다음달 목표 수정 여부")
    public BaseResponse<GetUserGoalRes> getUserGoal() {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);
            // userId로 userIdx 추출
            int userIdx = userService.getUserIdxByUserId(userId);

            GetUserGoalRes getUserGoalRes = goalService.getUserGoal(userIdx);
            return new BaseResponse<>(getUserGoalRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 유저 "다음달" 목표 조회 API
     * [GET] /users/goals/next
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/goals/next")
    @ApiOperation(value = "유저 다음달 목표 조회 API", notes = "목표 요일, 시간, 시간대, 다음달 목표 수정 여부")
    public BaseResponse<GetUserGoalRes> getUserGoalNext() {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);
            // userId로 userIdx 추출
            int userIdx = userService.getUserIdxByUserId(userId);

            GetUserGoalRes getUserGoalRes = goalService.getUserGoalNext(userIdx);
            return new BaseResponse<>(getUserGoalRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /** yummy 4
     * 이번달 정보 조회 API
     * [GET] /users/tmonth
     */
    @ResponseBody
    @GetMapping("/tmonth")
    public BaseResponse<GetMonthInfoRes> getMonthInfo() {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);
            GetMonthInfoRes getMonthInfoRes = userService.getMonthInfoRes(userId);
            return new BaseResponse<>(getMonthInfoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /**
     * 목표 수정 API
     * [PATCH] /users/goals
     */
    // Path-variable
    @ResponseBody
    @PatchMapping("/goals")
    @ApiOperation(value = "목표 수정", notes = "유저의 다음달 목표 수정")
    public BaseResponse<String> modifyGoal(@RequestBody PatchUserGoalReq patchUserGoalReq) throws JsonProcessingException {

        // Validaion 1. dayIdx 길이 확인
        if(patchUserGoalReq.getDayIdx().size() == 0) // 요일 0개 선택
            return new BaseResponse<>(new BaseException(BaseResponseStatus.MIN_DAYIDX).getStatus());
        if(patchUserGoalReq.getDayIdx().size() > 7)  // 요일 7개 초과 선택
            return new BaseResponse<>(new BaseException(BaseResponseStatus.MAX_DAYIDX).getStatus());

        // Validaion 2. dayIdx 숫자 범위 확인
        for (Integer dayIdx : patchUserGoalReq.getDayIdx()){
            if (dayIdx > 7 || dayIdx < 1)
                return new BaseResponse<>(new BaseException(BaseResponseStatus.INVALID_DAYIDX).getStatus());
        }

        // Validaion 3. dayIdx 중복된 숫자 확인
        Set<Integer> setDayIDx = new HashSet<>(patchUserGoalReq.getDayIdx());
        if(patchUserGoalReq.getDayIdx().size() != setDayIDx.size()) // dayIdx 크기를 set으로 변형시킨 dayIdx 크기와 비교. 크기가 다르면 중복된 값 존재
            return new BaseResponse<>(new BaseException(BaseResponseStatus.OVERLAP_DAYIDX).getStatus());

        // Validaion 4. walkGoalTime 범위 확인
        if(patchUserGoalReq.getWalkGoalTime() < 10) // 최소 산책 목표 시간 미만
            return new BaseResponse<>(new BaseException(BaseResponseStatus.MIN_WALK_GOAL_TIME).getStatus());
        if(patchUserGoalReq.getWalkGoalTime() > 240) // 최대 산책 목표 시간 초과
            return new BaseResponse<>(new BaseException(BaseResponseStatus.MAX_WALK_GOAL_TIME).getStatus());

        // Validaion 5. walkTimeSlot 범위 확인
        if(patchUserGoalReq.getWalkTimeSlot() > 7 || patchUserGoalReq.getWalkTimeSlot() < 1)
            return new BaseResponse<>(new BaseException(BaseResponseStatus.INVALID_WALK_TIME_SLOT).getStatus());

        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);
            // userId로 userIdx 추출
            int userIdx = userService.getUserIdxByUserId(userId);

            goalService.modifyGoalJPA(userIdx, patchUserGoalReq);

            String result ="목표가 수정되었습니다.";

            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /** yummy 5
     * 월별 발자국 개수 조회 API
     * [GET] /users/months/footprints?year=2021&month=2
     */
    @ResponseBody
    @GetMapping("/months/footprints")
    public BaseResponse<List<GetFootprintCount>> getMonthFootprints(
            @RequestParam(required = true) int year,
            @RequestParam(required = true) int month) throws BaseException
    {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);

            List<GetFootprintCount> getFootprintCounts = userService.getMonthFootprints(userId, year, month);
            return new BaseResponse<>(getFootprintCounts);
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** yummy 13
     * 매달 뱃지 상태 조회 API
     * [GET] /users/badges/status
     */
    @ResponseBody
    @GetMapping("/badges/status") //매달 첫 접속마다 요청되는 뱃지 확인 API - 이번달 획득 뱃지의 정보를 전달, 없으면 null 반환
    public BaseResponse<BadgeDateInfo> getMonthlyBadgeStatus() throws BaseException {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);

            BadgeDateInfo badgeDateInfo = badgeService.getMonthlyBadgeStatus(userId);
            return new BaseResponse<>(badgeDateInfo);
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** yummy 11
     * 사용자 전체 뱃지 조회 API
     * [GET] /users/badges
     */
    @ResponseBody
    @GetMapping("/badges")
    public BaseResponse<GetUserBadges> getUsersBadges() throws BaseException {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);

            GetUserBadges getUserBadges = badgeService.getUserBadges(userId);
            return new BaseResponse<>(getUserBadges);
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** yummy 12
     * 사용자 대표 뱃지 수정 API
     * [PATCH] /users/badges/title/:badgeidx
     */
    @ResponseBody
    @PatchMapping("/badges/title/{badgeIdx}")
    public BaseResponse<BadgeDateInfo> modifyRepBadge(@PathVariable("badgeIdx") int badgeIdx) throws BaseException {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);

            BadgeDateInfo patchRepBadgeDateInfo = badgeService.modifyRepBadge(userId, badgeIdx);
            return new BaseResponse<>(patchRepBadgeDateInfo);
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 유저 세부 정보 조회 API
     * [GET] /users/infos
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/infos") // (GET) 127.0.0.1:3000/users/:userIdx/infos
    @ApiOperation(value = "유저 통계 정보 조회", notes = "오늘 달성률, 이번달 달성률, 전체 누적 기록 횟수 + 주 O회(요일로 계산), 하루 몇분, 시간대 + 이전 3달 기준 요일별 산책 시간 백분율, 월별 기록 횟수, 월별 달성률")
    public BaseResponse<GetUserInfoRes> getUserInfo() {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);
            // userId로 userIdx 추출
            int userIdx = userService.getUserIdxByUserId(userId);

            // 1. user 달성률 정보
            UserInfoAchieve userInfoAchieve = userService.getUserInfoAchieve(userIdx);
            System.out.println("userInfoAchieve = " + userInfoAchieve);
            // 2. user 이번달 목표 정보
            GetUserGoalRes getUserGoalRes = goalService.getUserGoal(userIdx);
            System.out.println("getUserGoalRes = " + getUserGoalRes);
            // 3. user 통계 정보
            UserInfoStat userInfoStat = userService.getUserInfoStat(userIdx);
            System.out.println("userInfoStat = " + userInfoStat);

            // 4. 1+2+3
            return new BaseResponse<>(new GetUserInfoRes(userInfoAchieve,getUserGoalRes,userInfoStat));
//            return new BaseResponse<>(getUserInfoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 초기 정보 등록 API
     * [POST] /users/infos
     */
    // Path-variable
    @ResponseBody
    @PostMapping("/infos") // [POST] /users/infos
    @ApiOperation(value = "초기 회원 정보 등록", notes = "OAuth2.0 로그인 후 추가적으로 기입해야하는 필수/선택 회원 정보 입력")
    public BaseResponse<String> postUserInfo(@RequestBody PatchUserInfoReq patchUserInfoReq) throws JsonProcessingException {

        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);
            // userId로 userIdx 추출
            int userIdx = userService.getUserIdxByUserId(userId);

            // Validaion 1. userIdx 가 0 이하일 경우 exception
            if(userIdx <= 0)
                return new BaseResponse<>(new BaseException(BaseResponseStatus.INVALID_USERIDX).getStatus());

            // Validaion 2. dayIdx 길이 확인
            if(patchUserInfoReq.getDayIdx().size() == 0) // 요일 0개 선택
                return new BaseResponse<>(new BaseException(BaseResponseStatus.MIN_DAYIDX).getStatus());
            if(patchUserInfoReq.getDayIdx().size() > 7)  // 요일 7개 초과 선택
                return new BaseResponse<>(new BaseException(BaseResponseStatus.MAX_DAYIDX).getStatus());

            // Validaion 3. dayIdx 숫자 범위 확인
            for (Integer dayIdx : patchUserInfoReq.getDayIdx()){
                if (dayIdx > 7 || dayIdx < 1)
                    return new BaseResponse<>(new BaseException(BaseResponseStatus.INVALID_DAYIDX).getStatus());
            }

            // Validaion 4. dayIdx 중복된 숫자 확인
            Set<Integer> setDayIDx = new HashSet<>(patchUserInfoReq.getDayIdx());
            if(patchUserInfoReq.getDayIdx().size() != setDayIDx.size()) // dayIdx 크기를 set으로 변형시킨 dayIdx 크기와 비교. 크기가 다르면 중복된 값 존재
                return new BaseResponse<>(new BaseException(BaseResponseStatus.OVERLAP_DAYIDX).getStatus());

            // Validaion 5. walkGoalTime 범위 확인
            if(patchUserInfoReq.getWalkGoalTime() < 10) // 최소 산책 목표 시간 미만
                return new BaseResponse<>(new BaseException(BaseResponseStatus.MIN_WALK_GOAL_TIME).getStatus());
            if(patchUserInfoReq.getWalkGoalTime() > 240) // 최대 산책 목표 시간 초과
                return new BaseResponse<>(new BaseException(BaseResponseStatus.MAX_WALK_GOAL_TIME).getStatus());

            // Validaion 6. walkTimeSlot 범위 확인
            if(patchUserInfoReq.getWalkTimeSlot() > 7 || patchUserInfoReq.getWalkTimeSlot() < 1)
                return new BaseResponse<>(new BaseException(BaseResponseStatus.INVALID_WALK_TIME_SLOT).getStatus());


            try {
                int result = userService.postUserInfo(userIdx, patchUserInfoReq);

                String resultMsg = "정보 저장에 성공하였습니다.";
                if(result == 0)
                    resultMsg = "정보 저장에 실패하였습니다.";

                return new BaseResponse<>(resultMsg);
            } catch (BaseException exception) {
                return new BaseResponse<>((exception.getStatus()));
            }

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }


    }

    /**
     * 18 API
     * 태그 검색 API
     * [GET] /users/tags?tag=""
     */
    // Query String
    @ResponseBody
    @GetMapping("/tags")
    public BaseResponse<List<GetTagRes>> getTags(@RequestParam(required = false) String tag) {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);

            if (tag == null) { // Query String(검색어)를 입력하지 않았을 경우
                return new BaseResponse<>(new BaseException(BaseResponseStatus.NEED_TAG_INFO).getStatus());
            }
            List<GetTagRes> tagResult = new ArrayList<>();
            tagResult = userService.getTagResult(userId, tag);
            return new BaseResponse<>(tagResult);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /** yummy 25
     * 사용자 탈퇴 API
     * [GET] /users/unregister
     */
    @ResponseBody
    @DeleteMapping("/unregister")
    public BaseResponse<String> deleteUser() throws BaseException {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("유저 id: {}", userId);

            userService.deleteUser(userId);

            return new BaseResponse<>("탈퇴 성공:(");
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
