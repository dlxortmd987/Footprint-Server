package com.umc.footprint.src.notice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponse;
import com.umc.footprint.src.notice.model.GetNoticeListRes;
import com.umc.footprint.src.notice.model.GetNoticeRes;
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

    //
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

    //
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


}
