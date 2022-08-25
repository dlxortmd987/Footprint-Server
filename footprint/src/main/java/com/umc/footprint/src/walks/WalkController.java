package com.umc.footprint.src.walks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponse;
import com.umc.footprint.config.BaseResponseStatus;
import com.umc.footprint.src.walks.model.*;
import com.umc.footprint.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * API 33
     * 산책 코스 찜하기
     * [Patch] /walks/mark/:courseIdx
     * @param courseIdx
     * @return 찜하기 or 찜하기 취소
     */
    @PatchMapping("/mark/{courseIdx}")
    public BaseResponse<String> modifyMark(@PathVariable("courseIdx") int courseIdx) {
        try {
            String userId = jwtService.getUserId();
            log.debug("userId: {}", userId);

            String result = walkService.modifyMark(courseIdx, userId);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * API 38
     * 코스 정보 넘겨주기
     * [Get] /walks/path?courseName=
     * @param courseName 코스 이름
     * @return GetCourseInfoRes 코스 정보
     */
    @GetMapping("/path")
    public BaseResponse<GetCourseInfoRes> getCourseInfo(@RequestParam(name = "courseName") String courseName) {
        try {
            String userId = jwtService.getUserId();
            log.debug("userId: {}", userId);

             GetCourseInfoRes getCourseInfoRes = walkService.getCourseInfo(courseName, userId);
            return new BaseResponse<>(getCourseInfoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * API 39
     * 코스 저장
     * [Post] /walks/recommend
     * @param request
     * @return 코스 등록 or 코스 등록 실패
     */
    @PostMapping("/recommend")
    public BaseResponse<String> postCourseDetails(@RequestBody String request) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        PostCourseDetailsReq postCourseDetailsReq;
        try {
            postCourseDetailsReq = objectMapper.readValue(request, PostCourseDetailsReq.class);
        } catch (Exception exception) {
            return new BaseResponse<>(BaseResponseStatus.MODIFY_OBJECT_FAIL);
        }

        try {
            String result = walkService.postCourseDetails(postCourseDetailsReq);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * API 40
     * 코스 좋아요 설정
     * [POST] /walks/like/:courseIdx
     * @param courseIdx
     * @return 좋아요
     * @throws BaseException
     */
    @PatchMapping("/like/{courseIdx}")
    public BaseResponse<String> modifyCourseLike(@PathVariable(name = "courseIdx") Integer courseIdx) throws BaseException {
        String userId = jwtService.getUserId();
        log.debug("userId: {}", userId);

        try {
            String result = walkService.modifyCourseLike(courseIdx, userId);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * API 41
     * 코스 수정
     * [GET] /walks/recommend?courseName=""
     * @param request
     * @return
     */
    @PatchMapping("/recommend")
    public BaseResponse<String> modifyCourseDetails(@RequestBody String request) throws BaseException {
        String userId = jwtService.getUserId();
        log.debug("userId: {}", userId);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        PatchCourseDetailsReq postCourseDetailsReq;
        try {
            postCourseDetailsReq = objectMapper.readValue(request, PatchCourseDetailsReq.class);
        } catch (Exception exception) {
            return new BaseResponse<>(BaseResponseStatus.MODIFY_OBJECT_FAIL);
        }

        try {
            String result = walkService.modifyCourseDetails(postCourseDetailsReq, userId);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
