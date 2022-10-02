package com.umc.footprint.src.course.model.entity;

import com.umc.footprint.src.common.model.entity.Hashtag;
import com.umc.footprint.src.course.model.entity.Course;
import com.umc.footprint.src.course.model.vo.CourseStatus;
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
    private Integer courseTagIdx;

    @ManyToOne
    @JoinColumn(name = "courseIdx")
    private Course course;

    @OneToOne
    @JoinColumn(name = "hashtagIdx")
    private Hashtag hashtag;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourseStatus status;

    @Builder
    public CourseTag(Integer courseTagIdx, CourseStatus status) {
        this.courseTagIdx = courseTagIdx;
        this.status = status;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }
}
