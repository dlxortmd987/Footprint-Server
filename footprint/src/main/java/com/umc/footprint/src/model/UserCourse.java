package com.umc.footprint.src.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "UserCourse")
public class UserCourse {
    @Id
    @Column(name = "userCourseIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userCourseIdx;

    @Column(name = "userIdx")
    private Integer userIdx;

    @Column(name = "courseIdx")
    private Integer courseIdx;

    @Column(name = "walkIdx")
    private Integer walkIdx;

    @Column(name = "courseCount")
    private Long courseCount;

    @Builder
    public UserCourse(Integer userCourseIdx, Integer userIdx, Integer courseIdx, Integer walkIdx, Long courseCount) {
        this.userCourseIdx = userCourseIdx;
        this.userIdx = userIdx;
        this.courseIdx = courseIdx;
        this.walkIdx = walkIdx;
        this.courseCount = courseCount;
    }
}
