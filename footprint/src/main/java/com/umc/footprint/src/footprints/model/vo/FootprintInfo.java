package com.umc.footprint.src.footprints.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FootprintInfo {

    @ApiModelProperty(value = "발자국 인덱스",  hidden = true)
    private int footprintIdx;

    @ApiModelProperty(value = "발자국 좌표", example = "[37.4219983, -122.084]")
    private List<Double> coordinates;

    @ApiModelProperty(value = "발자국 string 좌표", hidden = true)
    private String strCoordinate;

    @ApiModelProperty(value = "발자국 작성 시간", example = "2022-05-28 09:40:18")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime recordAt;

    @ApiModelProperty(value = "글", example = "TEST_11")
    private String write;

    @ApiModelProperty(value = "발자국에 남긴 해시태그들", example = "[\"허무함\",\"인생\"]")
    private List<String> hashtagList;

    @ApiModelProperty(value = "산책 도중 발자국 남겼는지 여부", example = "1 or 0")
    private Integer onWalk;

    @ApiModelProperty(value = "산책 인덱스", hidden = true)
    private Integer walkIdx;

    @ApiModelProperty(value = "발자국 기록에 있는 사진들", example = "[\"https://mystepsbucket.s3.ap-northeast-2.amazonaws.com/03b494bb-8958-3669-a743-8c04ce4f85ac.jpg\", \"https://mystepsbucket.s3.ap-northeast-2.amazonaws.com/698e9224-8155-312b-b0fa-534cfec9f2e7.jpg\"]")
    private List<String> photos;

    @Builder
    public FootprintInfo(int footprintIdx, List<Double> coordinates, String strCoordinate, String write, LocalDateTime recordAt, int walkIdx, int onWalk, List<String> hashtagList, List<String> photos) {
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
