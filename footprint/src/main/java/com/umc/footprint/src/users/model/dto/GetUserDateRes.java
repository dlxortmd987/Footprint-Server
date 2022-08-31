package com.umc.footprint.src.users.model.dto;

import com.umc.footprint.src.walks.model.vo.UserDateWalk;
import lombok.*;

import java.util.ArrayList;

/*
 * userIdx, date에 해당하는 산책 정보 DTO
 * */

@Getter
@NoArgsConstructor
public class GetUserDateRes {

    private UserDateWalk userDateWalk;
    private ArrayList<String> hashtag;

    @Builder
    public  GetUserDateRes(UserDateWalk userDateWalk, ArrayList<String> hashtag){
        this.userDateWalk = userDateWalk;
        this.hashtag = hashtag;
    }

}
