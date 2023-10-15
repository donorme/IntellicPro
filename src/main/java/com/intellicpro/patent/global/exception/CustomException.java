package com.intellicpro.patent.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CustomException extends RuntimeException{ // @ExceptionHandler -> RuntimeException을 상속받기
    private final ErrorCode errorCode; // ALREADY_SIGNUP
    private final int status; // 400
    private final String message; //이미 가입된 사번입니다

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus().value();
    }
}
