package com.umc.footprint.src.walks.model.dto;

import com.umc.footprint.src.users.model.dto.GetUserDateRes;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
public class GetWalksRes {
    private List<GetUserDateRes> getWalks;
}
