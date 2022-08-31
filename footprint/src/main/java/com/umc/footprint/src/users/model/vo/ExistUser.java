package com.umc.footprint.src.users.model.vo;

import lombok.*;

@Getter
@NoArgsConstructor
public class ExistUser {
    private int userIdx;

    @Builder
    public ExistUser(int userIdx){
        this.userIdx = userIdx;
    }
}
