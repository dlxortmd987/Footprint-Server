package com.umc.footprint.src.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@RequiredArgsConstructor
public class GetGoalDays {
    private final int sun;
    private final int mon;
    private final int tue;
    private final int wed;
    private final int thu;
    private final int fri;
    private final int sat;
}
