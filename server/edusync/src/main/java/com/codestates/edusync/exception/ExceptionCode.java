package com.codestates.edusync.exception;

import lombok.Getter;

public enum ExceptionCode {
    DUPLICATED_EMAIL(409, "이메일을 찾을수 없습니다."),
    MEMBER_EXISTS(409, "이메일이 이미 존재"),
    MEMBER_NOT_FOUND(404, "멤버를 찾을수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "내부 서버 오류"),
    INVALID_PERMISSION(403, "권한이 유효하지 않습니다."),
    INACTIVE_MEMBER(403, "탈퇴한 회원입니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    STUDYGROUP_NOT_FOUND(404, "스터디를 찾을 수 없습니다."),
    STUDYGROUP_POST_COMMENT_NOT_FOUND(404, "댓글을 찾을 수 없습니다."),
    STUDYGROUP_POST_COMMENT_NOT_ALLOWED(403, "댓글은 본인과 스터디장만 삭제할 수 있습니다."),
    STUDYGROUP_POST_COMMENT_ALLOWED_ONLY_FOR_LEADER(403, "스터디장에게만 있는 권한입니다."),
    STUDYGROUP_POST_COMMENT_NOT_MATCHED(400, "해당 댓글은 해당 스터디 모집글에 존재하지 않습니다. 댓글의 식별자와 스터디 모집글의 식별자를 확인해주세요!"),
    TIME_SCHEDULE_NOT_FOUND(404, "일정이 존재하지 않습니다."),
    ;

    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
