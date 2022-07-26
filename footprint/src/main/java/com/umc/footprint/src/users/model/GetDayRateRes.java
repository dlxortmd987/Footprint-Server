package com.umc.footprint.src.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class GetDayRateRes { //일별 달성률(월별 달성률 보낼 때 리스트로 사용)
    private final int day; //날짜
    private final float rate; //달성률

    @Builder
    public GetDayRateRes(GetDayRateResInterface getDayRateResInterface) {
        this.day = getDayRateResInterface.getDay();
        this.rate = getDayRateResInterface.getRate();
    }
}
