package com.umc.footprint.src.users.model.dto;

import com.umc.footprint.src.walks.model.vo.UserDateWalk;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/*
 * userIdx, date에 해당하는 산책 정보 DTO
 * */

@Getter
@NoArgsConstructor
public class GetUserDateRes {

    @ApiModelProperty(value = "산책 정보 객체")
    private UserDateWalk userDateWalk;

    @ApiModelProperty(value = "산책의 해시태그들", example = " [\"#허니콤보\",\"#치맥\",\"#UMC\"]")
    private List<String> hashtag;

    @Builder
    public GetUserDateRes(UserDateWalk userDateWalk, List<String> hashtag){
        this.userDateWalk = userDateWalk;
        this.hashtag = hashtag;
    }

}
