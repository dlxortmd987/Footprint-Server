package com.umc.footprint.src.notice.model.dto;

import com.umc.footprint.src.notice.model.vo.NoticeList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetNoticeListRes {
    private int pageOn;
    private int pageTotal;
    private List<NoticeList> noticeList;

    @Builder
    public GetNoticeListRes(int pageOn, int pageTotal, List<NoticeList> noticeList) {
        this.pageOn = pageOn;
        this.pageTotal = pageTotal;
        this.noticeList = noticeList;
    }
}
