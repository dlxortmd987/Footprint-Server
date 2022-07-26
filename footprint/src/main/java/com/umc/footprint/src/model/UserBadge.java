package com.umc.footprint.src.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "UserBadge")
public class UserBadge {

    @Id
    @Column(name = "collectionIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer collectionIdx;

    @Column(name = "userIdx", nullable = false)
    private Integer userIdx;

    @Column(name = "badgeIdx", nullable = false)
    private Integer badgeIdx;

    @Column(name = "status", length = 20)
    private String status;

    @Builder
    public UserBadge(Integer collectionIdx, Integer userIdx, Integer badgeIdx, String status) {
        this.collectionIdx = collectionIdx;
        this.userIdx = userIdx;
        this.badgeIdx = badgeIdx;
        this.status = status;
    }

    @Builder
    public UserBadge(Integer userIdx, Integer badgeIdx, String status) {
        this.userIdx = userIdx;
        this.badgeIdx = badgeIdx;
        this.status = status;
    }
}
