package com.umc.footprint.src.walks.model.dto;

import com.umc.footprint.src.badge.model.vo.BadgeInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostWalkRes {
    private List<BadgeInfo> badgeInfoList;
    private Integer walkIdx;

    @Builder
    public PostWalkRes(List<BadgeInfo> badgeInfoList, Integer walkIdx) {
        this.badgeInfoList = badgeInfoList;
        this.walkIdx = walkIdx;
    }
}
