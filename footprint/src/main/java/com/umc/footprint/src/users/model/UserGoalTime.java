package com.umc.footprint.src.users.model;

import lombok.*;

@Getter
@NoArgsConstructor
public class UserGoalTime {
    private int walkGoalTime;
    private int walkTimeSlot;

    @Builder
    public UserGoalTime(int walkGoalTime, int walkTimeSlot){
        this.walkGoalTime = walkGoalTime;
        this.walkTimeSlot = walkTimeSlot;
    }
}
