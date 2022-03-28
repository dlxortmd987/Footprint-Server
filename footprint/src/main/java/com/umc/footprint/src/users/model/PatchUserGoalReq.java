package com.umc.footprint.src.users.model;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class PatchUserGoalReq {

    private int walkGoalTime;
    private int walkTimeSlot;
    private List<Integer> dayIdx;

    @Builder
    public PatchUserGoalReq(int walkGoalTime, int walkTimeSlot, List<Integer> dayIdx){
        this.walkGoalTime = walkGoalTime;
        this.walkTimeSlot = walkTimeSlot;
        this.dayIdx = dayIdx;
    }

    public void setDayIdx(List<Integer> dayIdx) {
        this.dayIdx = dayIdx;
    }
}
