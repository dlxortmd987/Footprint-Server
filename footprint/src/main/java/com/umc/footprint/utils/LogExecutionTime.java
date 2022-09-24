package com.umc.footprint.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 타겟 - 메서드
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 유지
public @interface LogExecutionTime {
}
