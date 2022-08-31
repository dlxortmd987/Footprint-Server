package com.umc.footprint.src.course.model.vo;

import com.umc.footprint.src.course.model.entity.Course;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CourseInfo implements Comparable<CourseInfo>{
    private int courseIdx;
    private double startLat;
    private double startLong;
    private String courseName;
    private Double courseDist;
    private int courseTime;
    private int courseCount;
    private int courseLike;
    private List<String> courseTags;
    private String courseImg;
    private boolean userCourseMark;

    @Builder
    public CourseInfo(int courseIdx, double startLat, double startLong, String courseName, Double courseDist,
                      int courseTime, int courseCount, int courseLike, List<String> courseTags, String courseImg,
                      boolean userCourseMark) {
        this.courseIdx = courseIdx;
        this.startLat = startLat;
        this.startLong = startLong;
        this.courseName = courseName;
        this.courseDist = courseDist;
        this.courseTime = courseTime;
        this.courseCount = courseCount;
        this.courseLike = courseLike;
        this.courseTags = courseTags;
        this.courseImg = courseImg;
        this.userCourseMark = userCourseMark;
    }

    public static CourseInfo of(Course course, int courseCount, String courseImgUrl, List<String> courseTags, boolean isMark) {
        return CourseInfo.builder()
                .courseIdx(course.getCourseIdx())
                .startLat(course.getStartCoordinate().getX())
                .startLong(course.getStartCoordinate().getY())
                .courseName(course.getCourseName())
                .courseDist(course.getLength())
                .courseTime(course.getCourseTime())
                .courseCount(courseCount)
                .courseLike(course.getLikeNum())
                .courseImg(courseImgUrl)
                .courseTags(courseTags)
                .userCourseMark(isMark)
                .build();
    }

    @Override
    public int compareTo(CourseInfo courseInfo){
        if(courseInfo.courseDist < courseDist){
            return 1;
        } else if(courseInfo.courseDist > courseDist){
            return -1;
        }
        return 0;
    }
}
