package com.umc.footprint.src.badge.model;

import com.umc.footprint.src.badge.model.vo.BadgeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BadgeDateInfo {
    private final BadgeInfo badgeInfo;
    private final String badgeDate;

    public BadgeDateInfo(Badge badge) {
        this.badgeInfo = BadgeInfo.builder()
                .badgeIdx(badge.getBadgeIdx())
                .badgeName(badge.getBadgeName())
                .badgeUrl(badge.getBadgeUrl())
                .build();

        this.badgeDate=badge.getBadgeDate().toString();
    }
}
