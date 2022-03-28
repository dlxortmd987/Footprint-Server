package com.umc.footprint.src.users.model;

import lombok.*;
import org.apache.catalina.User;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetUserGoalRes {
    private String month;
    private List<Integer> dayIdx;
    private UserGoalTime userGoalTime;
    private boolean goalNextModified;

    @Builder
    public GetUserGoalRes(String month, List<Integer> dayIdx, UserGoalTime userGoalTime, boolean goalNextModified){
        this.month = month;
        this.dayIdx = dayIdx;
        this.userGoalTime = userGoalTime;
        this.goalNextModified = goalNextModified;
    }

    public void setDayIdx(List<Integer> dayIdx) {
        this.dayIdx = dayIdx;
    }
}
