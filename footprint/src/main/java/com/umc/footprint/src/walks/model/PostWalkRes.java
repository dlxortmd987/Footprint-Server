package com.umc.footprint.src.walks.model;

import com.umc.footprint.src.model.Badge;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostWalkRes {
    private List<BadgeVO> badgeVOList;
    private Integer walkIdx;

    @Builder
    public PostWalkRes(List<BadgeVO> badgeVOList, Integer walkIdx) {
        this.badgeVOList = badgeVOList;
        this.walkIdx = walkIdx;
    }
}
