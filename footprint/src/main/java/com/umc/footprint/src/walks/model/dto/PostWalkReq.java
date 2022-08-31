package com.umc.footprint.src.walks.model.dto;

import com.umc.footprint.src.footprints.model.vo.FootprintInfo;
import com.umc.footprint.src.walks.model.vo.WalkInfo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostWalkReq {
    private WalkInfo walk;
    private List<FootprintInfo> footprintList;

    @Builder
    public PostWalkReq(WalkInfo walk, List<FootprintInfo> footprintList) {
        this.walk = walk;
        this.footprintList = footprintList;
    }

    public void setWalkStrCoordinate(WalkInfo newCoordinateWalk) {
        this.walk = newCoordinateWalk;
    }

    public void setConvertedFootprints(ArrayList<FootprintInfo> convertedFootprints) {
        this.footprintList = convertedFootprints;
    }
}