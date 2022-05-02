package com.umc.footprint.src.notice;

import com.umc.footprint.src.model.Notice;
import com.umc.footprint.src.notice.model.GetNoticeListRes;
import com.umc.footprint.src.notice.model.GetNoticeRes;
import com.umc.footprint.src.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public Page<GetNoticeRes> getNotice(){
        Page<Notice> page = noticeRepository.findAll(PageRequest.of(0,5));

        Page<GetNoticeRes> map = page.map(notice -> new GetNoticeRes(notice.getNoticeIdx(), notice.getNotice(), notice.getTitle(), notice.getImage(), notice.getCreateAt(), notice.getUpdateAt()));

        System.out.println("map = " + map.getContent());
        System.out.println("all = " + page.getTotalPages());
        System.out.println("all.getTotalElements() = " + page.getTotalElements());
        System.out.println("all.getContent() = " + page.getContent());

        return map;
    }

//    public GetNoticeListRes getNoticeList(){
//
//    }
//
//    public void postNotice(){
//
//    }


}
