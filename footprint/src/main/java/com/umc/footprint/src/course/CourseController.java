package com.umc.footprint.src.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponse;
import com.umc.footprint.src.users.UserProvider;
import com.umc.footprint.src.course.model.GetCourseInfoRes;
import com.umc.footprint.src.course.model.GetCourseListReq;
import com.umc.footprint.src.course.model.GetCourseListRes;
import com.umc.footprint.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final UserProvider userProvider;
    private final CourseService courseService;
    private final JwtService jwtService;

    @ResponseBody
    @PostMapping("/list")
    public BaseResponse<List<GetCourseListRes>> getCourseList(@RequestBody String request) throws BaseException, JsonProcessingException {

        // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
        String userId = jwtService.getUserId();
        log.debug("유저 id: {}", userId);
        // userId로 userIdx 추출
        int userIdx = userProvider.getUserIdx(userId);

        GetCourseListReq getWalkListReq = new ObjectMapper().readValue(request, GetCourseListReq.class);

        List<GetCourseListRes> courseList = courseService.getCourseList(getWalkListReq,userIdx);

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


}
