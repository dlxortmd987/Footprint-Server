package com.umc.footprint.src.users.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetGoalDays {
    private final int sun;
    private final int mon;
    private final int tue;
    private final int wed;
    private final int thu;
    private final int fri;
    private final int sat;

    @Builder
    public GetGoalDays(GoalDayInterface goalDayInterface) {
        this.sun = goalDayInterface.getSun();
        this.mon = goalDayInterface.getMon();
        this.tue = goalDayInterface.getTue();
        this.wed = goalDayInterface.getWed();
        this.thu = goalDayInterface.getThu();
        this.fri = goalDayInterface.getFri();
        this.sat = goalDayInterface.getSat();
    }
}
