package com.umc.footprint.src.badge.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class BadgeOrder {
    private final int badgeIdx;
    private final String badgeName;
    private final String badgeUrl;
    private final String badgeDate;
    private final int badgeOrder;
}
