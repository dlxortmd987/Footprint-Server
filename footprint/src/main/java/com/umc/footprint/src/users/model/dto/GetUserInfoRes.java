package com.umc.footprint.src.users.model.dto;

import com.umc.footprint.src.goal.model.dto.GetUserGoalRes;
import com.umc.footprint.src.users.model.vo.UserInfoAchieve;
import com.umc.footprint.src.users.model.vo.UserInfoStat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserInfoRes {
    private UserInfoAchieve userInfoAchieve;
    private GetUserGoalRes getUserGoalRes;
    private UserInfoStat userInfoStat;
}
