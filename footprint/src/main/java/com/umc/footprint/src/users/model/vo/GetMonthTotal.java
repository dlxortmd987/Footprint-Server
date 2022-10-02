package com.umc.footprint.src.users.model.vo;

import com.umc.footprint.src.goal.model.vo.GetMonthTotalInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMonthTotal implements GetMonthTotalInterface {
    private int monthTotalMin; //이번달 총 산책 시간(누적)
    private double monthTotalDistance; //이번달 총 산책 거리(누적)
    private int monthPerCal; //평균 칼로리

    public void avgCal(int dayCount){
        if(dayCount==0) return;
        this.monthPerCal = monthPerCal/dayCount;
    }

    public void convertSecToMin() {
        this.monthTotalMin = monthTotalMin/60;
    }
}
