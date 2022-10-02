package com.umc.footprint.src.course.model.dto.projection;

import java.time.LocalDateTime;

public interface CourseHashTagProjection {
    Integer getCourseIdx();

    String getAddress();

    String getDescription();

    Integer getWalkIdx();

    LocalDateTime getStartAt();

    LocalDateTime getEndAt();

    Double getDistance();

    String getCourseImg();

    void setCourseIdx(Integer courseIdx);

    void setAddress(String address);

    void setDescription(String description);

    void setWalkIdx(Integer walkIdx);

    void setStartAt(LocalDateTime startAt);

    void setEndAt(LocalDateTime endAt);

    void setDistance(Double distance);

    void setCourseImg(String courseImg);
}
