package com.umc.footprint.src.walks.model.dto;

import com.umc.footprint.src.users.model.dto.GetUserDateRes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetWalksRes {
    private List<GetUserDateRes> getWalks;

    @Builder
    public GetWalksRes(List<GetUserDateRes> getWalks) {
        this.getWalks = getWalks;
    }
}
