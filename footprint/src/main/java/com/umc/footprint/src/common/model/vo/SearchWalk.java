package com.umc.footprint.src.common.model.vo;

import com.umc.footprint.src.walks.model.vo.UserDateWalk;
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
