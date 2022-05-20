package com.umc.footprint.src.users.model;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchWalk {
    private UserDateWalk userDateWalk;
    private List<String> hashtag;

    @Builder
    public SearchWalk(UserDateWalk userDateWalk, List<String> hashtag) {
        this.userDateWalk = userDateWalk;
        this.hashtag = hashtag;
    }
}
