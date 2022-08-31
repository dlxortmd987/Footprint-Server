package com.umc.footprint.src.users.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoStat {
    private List<String> mostWalkDay;
    private List<Double> userWeekDayRate;
    private int thisMonthWalkCount;
    private List<Integer> monthlyWalkCount;
    private int thisMonthGoalRate;
    private List<Integer> monthlyGoalRate;
}
