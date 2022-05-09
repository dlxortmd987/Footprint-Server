package com.umc.footprint.src.users.model;

import lombok.*;

/*
 * userIdx, date에 해당하는 산책 정보
 * */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDateWalk {
    private int walkIdx;
    private String startTime;
    private String endTime;
    private String pathImageUrl;

    @Builder
    public UserDateWalk(int walkIdx, String startTime, String endTime, String pathImageUrl) {
        this.walkIdx = walkIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pathImageUrl = pathImageUrl;
    }

    public void setDecryptedPathImageUrl(String decrypt) {
        pathImageUrl = decrypt;
    }

    public void setWalkIdx(int getWalkIdx) {
        walkIdx = getWalkIdx;
    }
}
