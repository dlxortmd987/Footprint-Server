package com.umc.footprint.src.users.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.Timespan;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;


@Getter
@NoArgsConstructor
public class GetUserRes {
    private int userIdx;
    private String nickname;
    private String username;
    private String email;
    private String status;
    private int badgeIdx;
    private String badgeUrl;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp birth;
    private String sex;
    private int height;
    private int weight;
    private int walkNumber;

    @Builder
    public GetUserRes(int userIdx, String nickname, String username, String email, String status, int badgeIdx, String badgeUrl, Timestamp birth, String sex, int height, int weight, int walkNumber) {
        this.userIdx = userIdx;
        this.nickname = nickname;
        this.username = username;
        this.email = email;
        this.status = status;
        this.badgeIdx = badgeIdx;
        this.badgeUrl = badgeUrl;
        this.birth = birth;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.walkNumber = walkNumber;
    }
}

