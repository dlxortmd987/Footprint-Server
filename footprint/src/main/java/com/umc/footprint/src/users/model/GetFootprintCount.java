package com.umc.footprint.src.users.model;

import com.umc.footprint.src.walks.model.GetFootprintCountInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetFootprintCount implements GetFootprintCountInterface {
    private final int day;
    private final int walkCount;
}
