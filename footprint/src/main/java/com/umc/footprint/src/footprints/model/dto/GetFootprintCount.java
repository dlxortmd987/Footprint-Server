package com.umc.footprint.src.footprints.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetFootprintCount {

    @ApiModelProperty(value = "발자국을 기록한 날짜(일)", name = "day", dataType = "int", example = "1")
    private final int day;

    @ApiModelProperty(value = "해당 날짜에 기록한 발자국 개수", name = "walkCount", dataType = "int", example = "6")
    private final int walkCount;
}
