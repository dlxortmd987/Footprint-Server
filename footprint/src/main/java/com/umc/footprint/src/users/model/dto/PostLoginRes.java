package com.umc.footprint.src.users.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
public class PostLoginRes {

    @ApiModelProperty(value = "암호화된 유저 Id")
    private String jwtId;

    @ApiModelProperty(value = "사용자의 상태", example = "ACTIVE or ONGOING")
    private String status;

    @ApiModelProperty(value = "달이 변화되었는 지 체크하는 Flag", example = "true or false")
    private boolean checkMonthChanged;

    @Builder
    public PostLoginRes(String jwtId, String status, boolean checkMonthChanged) {
        this.jwtId = jwtId;
        this.status = status;
        this.checkMonthChanged = checkMonthChanged;
    }

    public void setCheckMonthChanged(boolean checkFlag) {
        this.checkMonthChanged = checkFlag;
    }

    public void setJwtId(String jwtId) {
        this.jwtId = jwtId;
    }
}
