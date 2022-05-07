package com.umc.footprint.src.notice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeList {
    private int noticeIdx;
    private String title;
    private boolean isNewNotice;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Builder
    public NoticeList(int noticeIdx, String title, boolean isNewNotice, LocalDateTime createAt, LocalDateTime updateAt) {
        this.noticeIdx = noticeIdx;
        this.title = title;
        this.isNewNotice = isNewNotice;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
