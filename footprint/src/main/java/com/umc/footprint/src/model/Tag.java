package com.umc.footprint.src.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Tag")
public class Tag {

    @Id
    @Column(name = "tagIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagIdx;

    @Column(name = "userIdx", nullable = false)
    private int userIdx;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtagIdx", insertable = false, updatable = false)
    private Hashtag hashtag;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "footprintIdx", insertable = false, updatable = false)
    private Footprint footprint;

    @Builder
    public Tag(int tagIdx, int userIdx, String status) {
        this.tagIdx = tagIdx;
        this.userIdx = userIdx;
        this.status = status;
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }

    public void setFootprint(Footprint footprint) {
        this.footprint = footprint;
    }
}