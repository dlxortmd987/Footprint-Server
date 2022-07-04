package com.umc.footprint.src.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
