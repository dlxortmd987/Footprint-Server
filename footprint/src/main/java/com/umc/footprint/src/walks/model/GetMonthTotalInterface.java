package com.umc.footprint.src.walks.model;

public interface GetMonthTotalInterface {
    int getMonthTotalMin(); //이번달 총 산책 시간(누적)
    double getMonthTotalDistance(); //이번달 총 산책 거리(누적)
    int getMonthPerCal(); //평균 칼로리

    void avgCal(int dayCount);

    void convertSecToMin();
}
