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
    private boolean isLastNotice;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Builder
    public GetNoticeRes(int noticeIdx, String title, String notice, String image, boolean isNewNotice,
                        boolean isLastNotice, LocalDateTime createAt, LocalDateTime updateAt) {
        this.noticeIdx = noticeIdx;
        this.title = title;
        this.notice = notice;
        this.image = image;
        this.isNewNotice = isNewNotice;
        this.isLastNotice = isLastNotice;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
