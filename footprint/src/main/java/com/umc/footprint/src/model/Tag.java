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
    private Integer tagIdx;

    @Column(name = "userIdx", nullable = false)
    private Integer userIdx;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @OneToOne(targetEntity = Hashtag.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtagIdx")
    private Hashtag hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "footprintIdx")
    private Footprint footprint;

    @Builder
    public Tag(Integer tagIdx, Integer userIdx, String status) {
        this.tagIdx = tagIdx;
        this.userIdx = userIdx;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        this.status = this.status == null ? "ACTIVE" : this.status;
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }

    public void setFootprint(Footprint footprint) {
        this.footprint = footprint;
        if (!footprint.getTagList().contains(this)) {
            footprint.addTagList(this);
        }
    }

    public void changeStatus(String status) {
        this.status = status;
    }
}