package com.umc.footprint.src.badge.model;

import com.umc.footprint.src.badge.model.Badge;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BadgeInfo {
    private final int badgeIdx;
    private final String badgeName;
    private final String badgeUrl;
    private final String badgeDate;

    public BadgeInfo(Badge badge) {
        this.badgeIdx=badge.getBadgeIdx();
        this.badgeName=badge.getBadgeName();
        this.badgeUrl=badge.getBadgeUrl();
        this.badgeDate=badge.getBadgeDate().toString();
    }
}
