package com.umc.footprint.src.notice.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetNoticeRes {
    private int noticeIdx;
    private String title;
    private String notice;
    private String image;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Builder
    public GetNoticeRes(int noticeIdx, String title, String notice, String image, LocalDateTime createAt, LocalDateTime updateAt) {
        this.noticeIdx = noticeIdx;
        this.title = title;
        this.notice = notice;
        this.image = image;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
