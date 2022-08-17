package com.umc.footprint.src.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CourseTag")
public class CourseTag {
    @Id
    @Column(name = "courseTagIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer badgeIdx;

    @Column(name = "courseIdx")
    private Integer courseIdx;

    @Column(name = "hashtagIdx")
    private Integer hashtagIdx;

    @Column(name = "status")
    private String status;

    @Builder
    public CourseTag(Integer badgeIdx, Integer courseIdx, Integer hashtagIdx, String status) {
        this.badgeIdx = badgeIdx;
        this.courseIdx = courseIdx;
        this.hashtagIdx = hashtagIdx;
        this.status = status;
    }
}
