package com.umc.footprint.src.notice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetVersionCheckRes {
    private Boolean whetherUpdate;
    private String version;

    @Builder
    public GetVersionCheckRes(Boolean whetherUpdate, String version) {
        this.whetherUpdate = whetherUpdate;
        this.version = version;
    }
}
