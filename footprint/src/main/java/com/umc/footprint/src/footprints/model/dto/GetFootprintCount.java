package com.umc.footprint.src.footprints.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetFootprintCount {
    private final int day;
    private final int walkCount;
}
