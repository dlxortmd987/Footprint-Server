package com.umc.footprint.src.users.model.dto;

import lombok.*;

/*
 * userIdx에 해당하는 오늘 산책 정보 DTO
 * */

@Getter
@NoArgsConstructor
public class GetUserTodayRes {
    private double goalRate;
    private int walkGoalTime;
    private int walkTime;
    private double distance;
    private int calorie;

    @Builder
    public GetUserTodayRes(double goalRate, int walkGoalTime, int walkTime, double distance, int calorie) {
        this.goalRate = goalRate;
        this.walkGoalTime = walkGoalTime;
        this.walkTime = walkTime;
        this.distance = distance;
        this.calorie = calorie;
    }
}
