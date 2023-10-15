package com.intellicpro.patent.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_EXIST_MEMBER("존재하지 않는 회원 입니다.", HttpStatus.NOT_FOUND),
    VALIDATION_FAILED("validation 오류", HttpStatus.BAD_REQUEST),
    ENUM_VALIDATION("enum class validation 오류", HttpStatus.BAD_REQUEST),
    FAILED_LOGIN("사번 혹은 비밀번호를 확인해주세요.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("알 수 없는 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ALREADY_SIGNUP("이미 가입된 사번입니다.", HttpStatus.BAD_REQUEST),

    ALREADY_CHECKED_IN("이미 출근 했습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
