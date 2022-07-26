package com.umc.footprint.src.notice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetNoticeRes {
    private int noticeIdx;
    private String title;
    private String notice;
    private String image;
    private boolean isNewNotice;
    private int preIdx;
    private int postIdx;
    private String createAt;
    private String updateAt;

    @Builder
    public GetNoticeRes(int noticeIdx, String title, String notice, String image, boolean isNewNotice, int preIdx, int postIdx, String createAt, String updateAt) {
        this.noticeIdx = noticeIdx;
        this.title = title;
        this.notice = notice;
        this.image = image;
        this.isNewNotice = isNewNotice;
        this.preIdx = preIdx;
        this.postIdx = postIdx;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
