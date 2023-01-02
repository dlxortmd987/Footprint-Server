package com.umc.footprint.src.notice.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetVersionCheckRes {

    @ApiModelProperty(value = "업데이트 여부")
    private Boolean whetherUpdate;

    @ApiModelProperty(value = "현재 배포중인 버전", example = "1.2.0")
    private String version;

    @Builder
    public GetVersionCheckRes(Boolean whetherUpdate, String version) {
        this.whetherUpdate = whetherUpdate;
        this.version = version;
    }
}
