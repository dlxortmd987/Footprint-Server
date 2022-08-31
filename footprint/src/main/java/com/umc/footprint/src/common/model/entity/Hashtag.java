package com.umc.footprint.src.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Hashtag")
public class Hashtag {

    @Id
    @Column(name = "hashtagIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hashtagIdx;

    @Column(name = "hashtag", length = 200)
    private String hashtag;

    @Builder
    public Hashtag(Integer hashtagIdx, String hashtag) {
        this.hashtagIdx = hashtagIdx;
        this.hashtag = hashtag;
    }

    public void decryptHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
