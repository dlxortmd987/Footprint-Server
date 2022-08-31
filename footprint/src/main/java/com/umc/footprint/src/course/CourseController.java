package com.umc.footprint.src.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponse;
import com.umc.footprint.config.BaseResponseStatus;
import com.umc.footprint.src.course.model.dto.GetCourseListRes;
import com.umc.footprint.src.users.UserProvider;
import com.umc.footprint.src.course.model.dto.GetCourseInfoRes;
import com.umc.footprint.src.course.model.dto.GetCourseListReq;
import com.umc.footprint.src.course.model.dto.GetCourseDetailsRes;
import com.umc.footprint.src.course.model.dto.GetWalkDetailsRes;
import com.umc.footprint.src.course.model.dto.PatchCourseDetailsReq;
import com.umc.footprint.src.course.model.dto.PostCourseDetailsReq;
import com.umc.footprint.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final UserService userService;
    private final CourseService courseService;
    private final JwtService jwtService;

    @ResponseBody
    @PostMapping("/list")
    public BaseResponse<GetCourseListRes> getCourseList(@RequestBody String request) throws BaseException, JsonProcessingException {

        // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
        String userId = jwtService.getUserId();
        log.debug("유저 id: {}", userId);
        // userId로 userIdx 추출
        int userIdx = userService.getUserIdxByUserId(userId);

        GetCourseListReq getWalkListReq = new ObjectMapper().readValue(request, GetCourseListReq.class);

        GetCourseListRes courseList = courseService.getCourseList(getWalkListReq,userIdx);

        return new BaseResponse<>(courseList);
    }


    @ResponseBody
    @GetMapping("/{courseIdx}/infos")
    public BaseResponse<GetCourseInfoRes> getCourseInfo(@PathVariable int courseIdx){

        try {
            GetCourseInfoRes courseInfo = courseService.getCourseInfo(courseIdx);

            return new BaseResponse<>(courseInfo);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * API 33
     * 산책 코스 찜하기
     * [Patch] /courses/mark/:courseIdx
     * @param courseIdx
     * @return 찜하기 or 찜하기 취소
     */
    @PatchMapping("/mark/{courseIdx}")
    public BaseResponse<String> modifyMark(@PathVariable("courseIdx") int courseIdx) {
        try {
            String userId = jwtService.getUserId();
            log.debug("userId: {}", userId);

            String result = courseService.modifyMark(courseIdx, userId);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * API 38
     * 코스 정보 넘겨주기 (코스 -> 코스, 코스 수정 시)
     * [Get] /courses/path?courseName=
     * @param courseName 코스 이름
     * @return GetCourseInfoRes 코스 정보
     */
    @GetMapping("/path")
    public BaseResponse<GetCourseDetailsRes> getCourseDetails(@RequestParam(name = "courseName") String courseName) {
        try {
            GetCourseDetailsRes getCourseInfoRes = courseService.getCourseDetails(courseName);
            return new BaseResponse<>(getCourseInfoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * API 39
     * 코스 저장
     * [Post] /courses/recommend
     * @param request
     * @return 코스 등록 or 코스 등록 실패
     */
    @PostMapping("/recommend")
    public BaseResponse<String> postCourseDetails(@RequestBody String request) throws BaseException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        PostCourseDetailsReq postCourseDetailsReq;

        String userId = jwtService.getUserId();
        log.debug("userId: {}", userId);

        try {
            postCourseDetailsReq = objectMapper.readValue(request, PostCourseDetailsReq.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new BaseResponse<>(BaseResponseStatus.MODIFY_OBJECT_FAIL);
        }

        try {
            String result = courseService.postCourseDetails(postCourseDetailsReq, userId);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * API 40
     * 코스 좋아요 설정
     * [POST] /courses/like/:courseIdx/:evaluate
     * @param courseIdx
     * @param evaluate
     * @return 좋아요
     * @throws BaseException
     */
    @PatchMapping("/like/{courseIdx}/{evaluate}")
    public BaseResponse<String> modifyCourseLike(@PathVariable(name = "courseIdx") Integer courseIdx, @PathVariable(name = "evaluate") Integer evaluate) throws BaseException {
        String userId = jwtService.getUserId();
        log.debug("userId: {}", userId);

        try {
            String result = courseService.modifyCourseLike(courseIdx, userId, evaluate);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * API 41
     * 코스 수정
     * [PATCH] /courses/recommend?courseName=""
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
            String result = courseService.modifyCourseDetails(postCourseDetailsReq, userId);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * API 44
     * 코스 정보 넘겨주기 (산책 -> 코스)
     * [Get] /courses/path/:walkNumber
     *
     * @param walkNumber 사용자의 n 번째 산책
     * @return GetWalkDetailsRes 산책 정보
     */
    @GetMapping("/path/{walkNumber}")
    public BaseResponse<GetWalkDetailsRes> getWalkDetails(@RequestParam(name = "walkNumber") Integer walkNumber) throws BaseException {
        String userId = jwtService.getUserId();
        log.debug("userId: {}", userId);

        try {
            GetWalkDetailsRes getWalkDetailsRes = courseService.getWalkDetails(walkNumber, userId);
            return new BaseResponse<>(getWalkDetailsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/list/mark")
    @ApiOperation(value = "찜한 코스 목록 조회", notes = "찜한 산책 목록이 없을 시 NOT_EXITST_MARK_COURSE 에러 발생")
    public BaseResponse<GetCourseListRes> getMarkCourseList() throws BaseException {
        String userId = jwtService.getUserId();
        try {
            GetCourseListRes getCourseListRes = courseService.getMarkCourses(userId);
            return new BaseResponse<>(getCourseListRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/list/recommend")
    @ApiOperation(value = "나의 추천 코스 목록 조회")
    public BaseResponse<GetCourseListRes> getMyRecommendCourseList() throws BaseException {
        String userId = jwtService.getUserId();
        try {
            GetCourseListRes getCourseListRes = courseService.getMyRecommendCourses(userId);
            return new BaseResponse<>(getCourseListRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
