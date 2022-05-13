package com.umc.footprint.src.notice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponse;
import com.umc.footprint.src.notice.model.*;
import com.umc.footprint.src.users.model.PostLoginReq;
import com.umc.footprint.src.users.model.PostLoginRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService){
        this.noticeService = noticeService;
    }

    // 페이징 처리된 리스트 목록 조회 GET API
    @ResponseBody
    @GetMapping("/list")
    public BaseResponse<GetNoticeListRes> getNoticeList(@RequestParam(required = true) int page,@RequestParam(required = true) int size){

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
    public BaseResponse<PostKeyNoticeRes> postKeyNotice(@RequestBody PostKeyNoticeReq postKeyNoticeReq) throws BaseException {

        try{
            PostKeyNoticeRes postKeyNoticeRes = noticeService.postNoticeKey(postKeyNoticeReq);

            return new BaseResponse<>(postKeyNoticeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
