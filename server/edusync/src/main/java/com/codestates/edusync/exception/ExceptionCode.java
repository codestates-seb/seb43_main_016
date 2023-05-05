package com.codestates.edusync.exception;

import lombok.Getter;

public enum ExceptionCode {

    DUPLICATED_EMAIL(409, "이메일을 찾을수 없습니다."),
    MEMBER_EXISTS(409, "이메일이 이미 존재"),
    MEMBER_NOT_FOUND(404, "멤버를 찾을수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "내부 서버 오류"),
    INVALID_PERMISSION(403, "권한이 유효하지 않습니다."),
    INACTIVE_MEMBER(403, "탈퇴한 회원입니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다.");

    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
