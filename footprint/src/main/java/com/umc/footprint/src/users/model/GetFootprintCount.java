package com.umc.footprint.src.users.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetFootprintCount {
    private final int day;
    private final int walkCount;
}
