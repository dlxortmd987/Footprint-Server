package com.umc.footprint.src.notice.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponse;
import com.umc.footprint.src.notice.NoticeService;
import com.umc.footprint.src.notice.model.dto.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/notices")
public class NoticeControllerV2 {

    private final NoticeService noticeService;

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
    public BaseResponse<PostKeyNoticeRes> postKeyNotice(@RequestBody PostKeyNoticeReq postKeyNoticeReq) throws BaseException, JsonProcessingException {

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
    public BaseResponse<GetVersionCheckRes> checkVersion(@PathVariable("userVersion") String userVersion) {
        GetVersionCheckRes versionCheck = noticeService.checkVersion(userVersion);

        return new BaseResponse<>(versionCheck);
    }
}
