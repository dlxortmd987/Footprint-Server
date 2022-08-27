package com.umc.footprint.src.walks.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class HashtagVO {
    private final Integer hashtagIdx;
    private final String hashtag;
}
