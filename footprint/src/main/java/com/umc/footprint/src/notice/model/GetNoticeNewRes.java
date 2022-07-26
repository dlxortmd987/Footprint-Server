package com.umc.footprint.src.notice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetNoticeNewRes {
    private boolean isNoticeNew;

    @Builder
    public GetNoticeNewRes(boolean isNoticeNew) {
        this.isNoticeNew = isNoticeNew;
    }
}
