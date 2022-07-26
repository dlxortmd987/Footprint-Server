package com.umc.footprint.src.users.model;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class PatchUserInfoReq {
    private String nickname;
    private String sex;
    private String birth;
    private int height;
    private int weight;
    private List<Integer> dayIdx;
    private int walkGoalTime;
    private int walkTimeSlot;
    private int walkNumber;

    @Builder
    public PatchUserInfoReq(String nickname, String sex, String birth, int height, int weight, List<Integer> dayIdx, int walkGoalTime, int walkTimeSlot) {
        this.nickname = nickname;
        this.sex = sex;
        this.birth = birth;
        this.height = height;
        this.weight = weight;
        this.dayIdx = dayIdx;
        this.walkGoalTime = walkGoalTime;
        this.walkTimeSlot = walkTimeSlot;
    }

    public void setDayIdx(List<Integer> dayIdx) {
        this.dayIdx = dayIdx;
    }

    public void setEncryptedNickname(String encryptedNickname) {
        this.nickname = encryptedNickname;
    }
}
