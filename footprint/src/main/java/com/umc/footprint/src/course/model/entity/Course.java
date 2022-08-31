package com.umc.footprint.src.course.model.entity;

import com.umc.footprint.src.course.model.dto.PatchCourseDetailsReq;
import com.umc.footprint.utils.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Course")
public class Course extends BaseEntity {

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
    private Double length;

    @Column(name = "courseTime")
    private Integer courseTime;

    @Column(name = "walkIdx")
    private Integer walkIdx;

    @Column(name = "userIdx")
    private Integer userIdx;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "likeNum")
    private Integer likeNum;

    @Column(name = "status")
    private String status;

    @Builder
    public Course(Integer courseIdx, String courseName, String courseImg, Point startCoordinate, String coordinate, String address, Double length, Integer courseTime, Integer walkIdx, Integer userIdx, String description, Integer likeNum, String status) {
        this.courseIdx = courseIdx;
        this.courseName = courseName;
        this.courseImg = courseImg;
        this.startCoordinate = startCoordinate;
        this.coordinate = coordinate;
        this.address = address;
        this.length = length;
        this.courseTime = courseTime;
        this.walkIdx = walkIdx;
        this.userIdx = userIdx;
        this.description = description;
        this.likeNum = likeNum;
        this.status = status;
    }

    /**
     * likeNum 1 증가
     */
    public void addLikeNum() {
        this.likeNum += 1;
    }

    /**
     * 코스 수정
     * @param patchCourseDetailsReq
     * @param startCoordinate
     * @param encryptedCoordinates
     */
    public void updateCourse(PatchCourseDetailsReq patchCourseDetailsReq, Point startCoordinate, String encryptedCoordinates) {
        this.courseName = patchCourseDetailsReq.getCourseName();
        this.courseImg = patchCourseDetailsReq.getCourseImg();
        this.startCoordinate = startCoordinate;
        this.coordinate = encryptedCoordinates;
        this.address = patchCourseDetailsReq.getAddress();
        this.length = patchCourseDetailsReq.getLength();
        this.courseTime = patchCourseDetailsReq.getCourseTime();
        this.description = patchCourseDetailsReq.getDescription();
    }

    public void updateStatus(String status) {
        this.status = status;
    }
}
