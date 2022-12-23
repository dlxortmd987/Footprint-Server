package com.umc.footprint.src.notice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponse;
import com.umc.footprint.src.notice.model.dto.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v2/notices")
public class NoticeControllerV2 {

    private final NoticeService noticeService;

    @Autowired
    public NoticeControllerV2(NoticeService noticeService){
        this.noticeService = noticeService;
    }

    // 페이징 처리된 리스트 목록 조회 GET API
    @ResponseBody
    @GetMapping("/list")
    @ApiOperation(value = "공지사항 목록 조회", notes = "paging 처리된 공지사항 목록 조회")
    public BaseResponse<GetNoticeListRes> getNoticeList(@RequestParam(required = true) int page, @RequestParam(required = true) int size){

        try {
            GetNoticeListRes noticeList = noticeService.getNoticeList(page, size);

            return new BaseResponse<>(noticeList);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 개별 리스트 조회 GET API
    @ResponseBody
    @GetMapping("")
    @ApiOperation(value = "공지사항 조회", notes = "특정 공지사항 내용 조회")
    public BaseResponse<Optional<GetNoticeRes>> getNotice(@RequestParam(required = true) int idx) throws BaseException {

        try{
            Optional<GetNoticeRes> notice = noticeService.getNotice(idx);

            return new BaseResponse<>(notice);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 새로운 Notice가 있는지 확인하는 GET API
    @ResponseBody
    @GetMapping("/new")
    @ApiOperation(value = "새로운 공지 여부 조회", notes = "새롭게 게시된 공지사항이 있는지 여부 확인")
    public BaseResponse<GetNoticeNewRes> getNoticeNew() throws BaseException {

        try{
            GetNoticeNewRes noticeNew = noticeService.getNoticeNew();

            return new BaseResponse<>(noticeNew);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 주요 공지 GET API
    @ResponseBody
    @PostMapping("/key")
    @ApiOperation(value = "주요 공지 조회", notes = "유저가 확인하지 않은 주요 공지 사항의 내용을 모두 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkedKeyNoticeIdxList", value = "유저가 이미 확인한 주요 공지 인덱스 리스트", dataType = "List<Integer>", paramType = "body", required = true),
    })
    public BaseResponse<PostKeyNoticeRes> postKeyNotice(@RequestBody String request) throws BaseException, JsonProcessingException {

        PostKeyNoticeReq postKeyNoticeReq = new ObjectMapper().readValue(request, PostKeyNoticeReq.class);

        try{

            PostKeyNoticeRes postKeyNoticeRes = noticeService.postNoticeKey(postKeyNoticeReq);

            return new BaseResponse<>(postKeyNoticeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 버전 조회 API
     * [GET] /notices/version/:userVersion
     */
    @GetMapping("/version/{userVersion}")
    @ApiOperation(value = "유저의 버전 확인", notes = "유저의 버전이 맞지 않으면 강제 업데이트를 시키기 위한 용도")
    public BaseResponse<GetVersionCheckRes> checkVersion(@PathVariable("userVersion") @ApiParam(value = "유저의 버전", example = "1.0.0") String userVersion) {
        GetVersionCheckRes versionCheck = noticeService.checkVersion(userVersion);

        return new BaseResponse<>(versionCheck);
    }
}
