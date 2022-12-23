package com.umc.footprint.src.users.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetTagRes {

    @ApiModelProperty(value = "산책 요일", example = "2022.01.17 월")
    private String walkAt;

    @ApiModelProperty(value = "요일별 산책 정보")
    private List<GetUserDateRes> walks;

    @Builder
    public GetTagRes(String walkAt, List<GetUserDateRes> walks) {
        this.walkAt = walkAt;
        this.walks = walks;
    }
}
