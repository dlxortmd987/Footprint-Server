package com.umc.footprint.src.walks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponse;
import com.umc.footprint.src.users.UserProvider;
import com.umc.footprint.src.walks.model.GetWalkInfo;
import com.umc.footprint.src.walks.model.PostWalkReq;
import com.umc.footprint.src.walks.model.PostWalkRes;
import com.umc.footprint.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/walks")
public class WalkController {

    private final WalkService walkService;
    private final JwtService jwtService;

    /**
     * 실시간 처리 API
     * [Post] /walks
     */
    @ResponseBody
    @PostMapping("") // (POST) 127.0.0.1:3000/walks/
    @ApiOperation(value = "산책 기록 저장")
    public BaseResponse<List<PostWalkRes>> saveRecord(@RequestBody String request) throws BaseException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        PostWalkReq postWalkReq = objectMapper.readValue(request, PostWalkReq.class);

        // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
        String userId = jwtService.getUserId();
        log.debug("userId = " + userId);
        // userId로 userIdx 추출

        try {
            List<PostWalkRes> postWalkResList = walkService.saveRecord(userId, postWalkReq);
            return new BaseResponse<>(postWalkResList);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{walkIdx}") // (GET) 127.0.0.1:3000/walks/{walkIdx}
    public BaseResponse<GetWalkInfo> getWalkInfo(@PathVariable("walkIdx") int walkIdx) {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("userId: {}", userId);

            // Walk 테이블 전체에서 인덱스
            GetWalkInfo getWalkInfo = walkService.getWalkInfo(walkIdx, userId);

            return new BaseResponse<>(getWalkInfo);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //yummy 21
    //해당 산책의 기록(발자국) 전체 삭제
    @ResponseBody
    @PatchMapping("/{walkIdx}/status") // (Patch) 127.0.0.1:3000/walks/{walkIdx}/status
    public BaseResponse<String> deleteWalk(@PathVariable("walkIdx") int walkIdx) {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("userId: {}", userId);

            String result = walkService.deleteWalk(walkIdx, userId);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // API 33
    // 산책 코스 찜하기
    @PatchMapping("/mark") // (Patch) /walks/mark
    public BaseResponse<String> modifyMark() {
        return null;
    }

    // API 38
    // 코스 좌표 넘겨주기
    @GetMapping("/path") // (Get) /walks/path
    public BaseResponse<String> getPath() {
        return null;
    }

    // API 39
    // 공유할 코스 세부사항 저장
    @PostMapping("/recommend") // (Post) /walks/recommend
    public BaseResponse<String> postCourseDetails() {
        return null;
    }

    // API 40
    // 코스 좋아요 설정
    @PostMapping("/like") // (Post) /walks/like
    public BaseResponse<String> postCourseLike() {
        return null;
    }

    // API 41
    // 코스 수정
    @PatchMapping("/recommend") // (Patch) /walks/recommend
    public BaseResponse<String> modifyCourseDetails() {
        return null;
    }
}
