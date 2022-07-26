package com.umc.footprint.src.footprints.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetFootprintRes {
    private int footprintIdx;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime recordAt;
    private String write;
    private List<String> photoList;
    private List<String> tagList;
    private int onWalk;

    @Builder
    public GetFootprintRes(int footprintIdx, LocalDateTime recordAt, String write, List<String> photoList, List<String> tagList, int onWalk) {
        this.footprintIdx = footprintIdx;
        this.recordAt = recordAt;
        this.write = write;
        this.photoList = photoList;
        this.tagList = tagList;
        this.onWalk = onWalk;
    }
}
