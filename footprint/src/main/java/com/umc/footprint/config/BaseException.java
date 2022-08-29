package com.umc.footprint.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class BaseException extends Throwable {
    private final BaseResponseStatus status;
}
