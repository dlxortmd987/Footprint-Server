package com.umc.footprint.src.users;

import com.umc.footprint.config.BaseResponseStatus;
import com.umc.footprint.src.users.model.GetUserTodayRes;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.umc.footprint.src.users.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;;
import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponse;
import com.umc.footprint.config.BaseResponseStatus.*;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;

    public UserController(UserProvider userProvider, UserService userService) {
        this.userProvider = userProvider;
        this.userService = userService;
    }


    @GetMapping("/{useridx}/today")
    public BaseResponse<List<GetUserTodayRes>> getToday(@PathVariable("useridx") int userIdx){
        try{
            List<GetUserTodayRes> userTodayRes = userProvider.getUserToday(userIdx);

            return new BaseResponse<>(userTodayRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 정보 조회 API
     * [GET] /users/:userIdx
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:3000/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
        try {
            GetUserRes getUserRes = userProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 목표 수정 API
     * [POST] /users/:useridx/goals
     */
    // Path-variable
    @ResponseBody
    @PostMapping("/{useridx}/goals") // [POST] /users/:useridx/goals
    public BaseResponse<String> postGoal(@PathVariable("useridx") int userIdx, @RequestBody PostUserGoalReq postUserGoalReq){

        // Validaion 1. userIdx 가 0 이하일 경우 exception
        if(userIdx <= 0)
            return new BaseResponse<>(new BaseException(BaseResponseStatus.INVALID_USERIDX).getStatus());

        // Validaion 2. dayIdx 길이 확인
        if(postUserGoalReq.getDayIdx().size() == 0) // 요일 0개 선택
            return new BaseResponse<>(new BaseException(BaseResponseStatus.MIN_DAYIDX).getStatus());
        if(postUserGoalReq.getDayIdx().size() > 7)  // 요일 7개 초과 선택
            return new BaseResponse<>(new BaseException(BaseResponseStatus.MAX_DAYIDX).getStatus());

        // Validaion 3. dayIdx 숫자 범위 확인
        for (Integer dayIdx : postUserGoalReq.getDayIdx()){
            if (dayIdx > 7 || dayIdx < 1)
                return new BaseResponse<>(new BaseException(BaseResponseStatus.INVALID_DAYIDX).getStatus());
        }

        // Validaion 4. dayIdx 중복된 숫자 확인
        Set<Integer> setDayIDx = new HashSet<>(postUserGoalReq.getDayIdx());
        if(postUserGoalReq.getDayIdx().size() != setDayIDx.size()) // dayIdx 크기를 set으로 변형시킨 dayIdx 크기와 비교. 크기가 다르면 중복된 값 존재
            return new BaseResponse<>(new BaseException(BaseResponseStatus.OVERLAP_DAYIDX).getStatus());

        // Validaion 5. walkGoalTime 범위 확인
        if(postUserGoalReq.getWalkGoalTime() < 10) // 최소 산책 목표 시간 미만
            return new BaseResponse<>(new BaseException(BaseResponseStatus.MIN_WALK_GOAL_TIME).getStatus());
        if(postUserGoalReq.getWalkGoalTime() > 720) // 최대 산책 목표 시간 초과
            return new BaseResponse<>(new BaseException(BaseResponseStatus.MAX_WALK_GOAL_TIME).getStatus());

        // Validaion 6. walkTimeSlot 범위 확인
        if(postUserGoalReq.getWalkTimeSlot() > 8 || postUserGoalReq.getWalkTimeSlot() < 1)
            return new BaseResponse<>(new BaseException(BaseResponseStatus.INVALID_WALK_TIME_SLOT).getStatus());


        try {
            int result = userService.postGoal(userIdx, postUserGoalReq);

            String resultMsg = "목표 저장에 성공하였습니다.";
            if(result == 0)
                resultMsg = "목표 저장에 실패하였습니다.";

            return new BaseResponse<>(resultMsg);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
