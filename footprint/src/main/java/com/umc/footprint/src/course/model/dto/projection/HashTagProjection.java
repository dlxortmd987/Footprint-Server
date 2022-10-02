package com.umc.footprint.src.course.model.dto.projection;

public interface HashTagProjection {
    Integer getHashtagIdx();

    String getHashtag();

    void setHashtagIdx(Integer hashtagIdx);

    void setHashtag(String hashtag);
}
