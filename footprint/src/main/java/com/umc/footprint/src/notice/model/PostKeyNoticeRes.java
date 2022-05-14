package com.umc.footprint.src.notice.model;

import com.umc.footprint.src.model.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostKeyNoticeRes {
    private List<Notice> keyNoticeList;

    @Builder
    public PostKeyNoticeRes(List<Notice> keyNoticeList) {
        this.keyNoticeList = keyNoticeList;
    }
}
