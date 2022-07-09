package com.umc.footprint.src.users.model;

import com.umc.footprint.src.model.Badge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class GetUserBadges {
    private final BadgeInfo repBadgeInfo;
    private final List<BadgeOrder> badgeList;
}
