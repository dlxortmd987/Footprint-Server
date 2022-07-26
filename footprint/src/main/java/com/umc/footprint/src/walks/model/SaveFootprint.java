package com.umc.footprint.src.walks.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class SaveFootprint {

    @ApiModelProperty(example = "발자국 인덱스")
    private int footprintIdx;

    @ApiModelProperty(example = "발자국 좌표")
    private List<Double> coordinates;

    @ApiModelProperty(example = "발자국 string 좌표", hidden = true)
    private String strCoordinate;

    @ApiModelProperty(example = "발자국 작성 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime recordAt;

    @ApiModelProperty(example = "글")
    private String write;

    private List<String> hashtagList;

    @ApiModelProperty(example = "산책 활성 여부")
    private Integer onWalk;

    @ApiModelProperty(example = "산책 인덱스", hidden = true)
    private Integer walkIdx;

    // Url 저장
    private List<String> photos;

    @Builder
    public SaveFootprint(int footprintIdx, List<Double> coordinates, String strCoordinate, String write, LocalDateTime recordAt, int walkIdx, int onWalk, List<String> hashtagList, List<String> photos) {
        this.footprintIdx = footprintIdx;
        this.coordinates = coordinates;
        this.strCoordinate = strCoordinate;
        this.write = write;
        this.recordAt = recordAt;
        this.walkIdx = walkIdx;
        this.onWalk = onWalk;
        this.hashtagList = hashtagList;
        this.photos = photos;
    }





    public void setWalkIdxOfFootprint(int walkIdx) {
        this.walkIdx = walkIdx;
    }

    public void setFootprintIdx(int footprintIdx) {
        this.footprintIdx = footprintIdx;
    }
}
