package com.umc.footprint.src.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CourseTag")
public class CourseTag {
    @Id
    @Column(name = "courseTagIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseTagIdx;

    @OneToOne(targetEntity = Course.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "courseIdx")
    private Course course;

    @OneToMany(targetEntity = Hashtag.class, fetch = FetchType.LAZY, mappedBy = "hashtag")
    private List<Hashtag> hashtagList;

    @Column(name = "status")
    private String status;

    @Builder
    public CourseTag(Integer courseTagIdx, Course course, List<Hashtag> hashtagList, String status) {
        this.courseTagIdx = courseTagIdx;
        this.course = course;
        this.hashtagList = hashtagList;
        this.status = status;
    }

    public void addHashtag(Hashtag hashtag) {
        this.hashtagList.add(hashtag);
    }
}
