package com.umc.footprint.src.notice.model;

import com.umc.footprint.src.model.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostKeyNoticeRes {
    private List<GetNoticeRes> keyNoticeList;

    @Builder
    public PostKeyNoticeRes(List<GetNoticeRes> keyNoticeList) {
        this.keyNoticeList = keyNoticeList;
    }
}
