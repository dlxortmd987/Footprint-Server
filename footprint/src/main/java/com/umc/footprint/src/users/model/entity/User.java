package com.umc.footprint.src.users.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "User")
public class User {

    @Id
    @Column(name = "userIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userIdx;

    @Column(name = "userId", length = 200, nullable = false)
    private String userId;

    @Column(name = "nickname", length = 15)
    private String nickname;

    @Column(name = "badgeIdx")
    private Integer badgeIdx;

    @Column(name = "username", length = 200, nullable = false)
    private String username;

    @Column(name = "birth")
    private LocalDateTime birth;

    @Column(name = "sex", length = 6)
    private String sex;

    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "providerType", length = 20)
    private String providerType;

    @Column(name = "status")
    private String status;

    @Column(name = "logAt")
    private LocalDateTime logAt;

    @Builder
    public User(Integer userIdx, String userId, String nickname, Integer badgeIdx, String username, LocalDateTime birth, String sex, String email, Integer height, Integer weight, String providerType, String status, LocalDateTime logAt) {
        this.userIdx = userIdx;
        this.userId = userId;
        this.nickname = nickname;
        this.badgeIdx = badgeIdx;
        this.username = username;
        this.birth = birth;
        this.sex = sex;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.providerType = providerType;
        this.status = status;
        this.logAt = logAt;
    }

    // default 값으로 초기화
    @PrePersist
    public void prePersist() {
        this.nickname = this.nickname == null ? "nickname" : this.nickname;
        this.status = this.status == null ? "ONGOING" : this.status;
        this.logAt = this.logAt == null ? LocalDateTime.of(1900,01,01,01,01,01) : this.logAt;
    }

    public void updateLogAtNow(LocalDateTime now) {
        this.logAt = now;
    }

    public void setBadgeIdx(int badgeIdx) {
        this.badgeIdx = badgeIdx;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBirth(LocalDateTime birth) {
        this.birth = birth;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
