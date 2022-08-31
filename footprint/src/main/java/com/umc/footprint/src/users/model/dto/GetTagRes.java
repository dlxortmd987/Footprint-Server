package com.umc.footprint.src.users.model.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetTagRes {
    private String walkAt;
    private List<GetUserDateRes> walks;

    @Builder
    public GetTagRes(String walkAt, List<GetUserDateRes> walks) {
        this.walkAt = walkAt;
        this.walks = walks;
    }
}
