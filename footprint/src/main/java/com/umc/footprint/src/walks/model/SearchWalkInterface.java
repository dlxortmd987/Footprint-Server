package com.umc.footprint.src.walks.model;

import java.time.LocalDateTime;

public interface SearchWalkInterface {
    Integer getWalkIdx();
    String getHashtag();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
    String getPathImageUrl();
}
