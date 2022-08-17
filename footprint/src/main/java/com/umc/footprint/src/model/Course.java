package com.umc.footprint.src.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Course")
public class Course {

    @Id
    @Column(name = "courseIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseIdx;

    @Column(name = "courseName")
    private String courseName;

    @Column(name = "courseImg")
    private String courseImg;

    @Column(name = "startCoordinate")
    private Point startCoordinate;

    @Column(name = "coordinate", columnDefinition = "longtext")
    private String coordinate;

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @Column(name = "length")
    private Float length;

    @Column(name = "courseTime")
    private LocalDateTime courseTime;

    @Column(name = "walkIdx")
    private Integer walkIdx;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "markNum")
    private Integer markNum;

    @Column(name = "likeNum")
    private Integer likeNum;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @Column(name = "updateAt")
    private LocalDateTime updateAt;

    @Column(name = "status")
    private String status;

    @Builder
    public Course(Integer courseIdx, String courseName, String courseImg, Point startCoordinate, String coordinate, String address, Float length, LocalDateTime courseTime, Integer walkIdx, String description, Integer markNum, Integer likeNum, LocalDateTime createAt, LocalDateTime updateAt, String status) {
        this.courseIdx = courseIdx;
        this.courseName = courseName;
        this.courseImg = courseImg;
        this.startCoordinate = startCoordinate;
        this.coordinate = coordinate;
        this.address = address;
        this.length = length;
        this.courseTime = courseTime;
        this.walkIdx = walkIdx;
        this.description = description;
        this.markNum = markNum;
        this.likeNum = likeNum;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }
}
