package com.umc.footprint.src.notice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.footprint.config.BaseResponse;
import com.umc.footprint.src.notice.model.GetNoticeRes;
import com.umc.footprint.src.users.model.PostLoginReq;
import com.umc.footprint.src.users.model.PostLoginRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("")
    public BaseResponse<Page<GetNoticeRes>> getNotice(){

        Page<GetNoticeRes> page =  noticeService.getNotice();

        return new BaseResponse<>(page);
    }

//    //
//    @ResponseBody
//    @GetMapping("")
//    public BaseResponse<GetNoticeRes> getNoticeList(@RequestBody String request){
//
//    }
//
//    //
//    @ResponseBody
//    @PostMapping("")
//    public BaseResponse<postNoticeRes> postNotice(@RequestBody String request){
//
//
//    }


}
