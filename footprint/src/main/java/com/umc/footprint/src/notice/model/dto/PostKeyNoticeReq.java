package com.umc.footprint.src.notice.model.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostKeyNoticeReq {
    private List<Integer> checkedKeyNoticeIdxList;

    @Builder
    public PostKeyNoticeReq(List<Integer> checkedKeyNoticeIdxList) {
        this.checkedKeyNoticeIdxList = checkedKeyNoticeIdxList;
    }
}
