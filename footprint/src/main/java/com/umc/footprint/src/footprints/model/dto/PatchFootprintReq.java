package com.umc.footprint.src.footprints.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
public class PatchFootprintReq {
    private String write;
    private List<MultipartFile> photos; // 사진
    private List<String> tagList; // 태그

    @Builder
    public PatchFootprintReq(String write, List<MultipartFile> photos, List<String> tagList) {
        this.write = write;
        this.photos = photos;
        this.tagList = tagList;
    }

}
