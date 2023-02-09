package com.umc.footprint.src.users.model.dto;

import com.umc.footprint.src.users.model.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
public class PostLoginReq {

    @ApiModelProperty(value = "유저 id", example = "1923191")
    private String userId;

    @ApiModelProperty(value = "이름", example = "blue")
    private String username;

    @ApiModelProperty(value = "이메일", example = "example@gmail.com")
    private String email;

    @ApiModelProperty(value = "Provider", example = "kakao or google")
    private String providerType;

    @Builder
    public PostLoginReq(String userId, String username, String email, String providerType) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.providerType = providerType;
    }

    public void setEncryptedInfos(String encryptedUsername, String encryptedEmail) {
        this.username = encryptedUsername;
        this.email = encryptedEmail;
    }

    public User toUserEntity() {
        return User.builder()
                .userId(userId)
                .username(username)
                .email(email)
                .providerType(providerType)
                .badgeIdx(0)
                .build();
    }
}
