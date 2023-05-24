package com.codestates.edusync.exception;

import lombok.Getter;

public enum ExceptionCode {
    /**
     * Member 관련 에러 코드
     */
    YOU_ARE_NOT_STUDYGROUP_LEADER(403, "스터디 리더만 가능합니다."),
    MEMBER_NOT_FOUND(404, "멤버를 찾을수 없습니다."),
    DUPLICATED_EMAIL(409, "이메일을 찾을수 없습니다."),
    MEMBER_EXISTS_EMAIL(409, "이메일이 이미 존재"),
    MEMBER_EXISTS_NICKNAME(409, "닉네임이 이미 존재"),
    PASSWORD_ERROR(400, "잘못된 비밀번호"),
    MEMBER_ALREADY_ACTIVE(409, "회원이 이미 active 상태입니다."),

    /**
     * 인증 관련 에러 코드
     */
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    INVALID_PERMISSION(403, "권한이 유효하지 않습니다."),
    INACTIVE_MEMBER(403, "탈퇴한 회원입니다."),

    /**
     * 스터디 그룹에 대한 에러 코드
     */
    STUDYGROUP_RECRUITED_NOT_MODIFIED(403, "스터디가 이미 모집 완료 되었습니다."),
    STUDYGROUP_NOT_FOUND(404, "스터디를 찾을 수 없습니다."),
    STUDYGROUP_PRIVILEGES_MEMBER_NOT_FOUND(404, "스터디 권한을 위임하기 위한 멤버를 찾을수 없습니다. 스터디 그룹의 맴버인지 확인해주세요."),

    /**
     * 스터디 그룹 모집글에 대한 댓글 관련 에러 코드
     */
    STUDYGROUP_POST_COMMENT_AUTHENTICATION_NOT_NULL(400, "로그인 된 회원 정보를 찾을 수 없습니다. 토큰 정보를 포함해야합니다!"),
    STUDYGROUP_POST_COMMENT_NOT_ALLOWED(403, "댓글은 본인과 스터디장만 삭제할 수 있습니다."),
    STUDYGROUP_POST_COMMENT_ALLOWED_ONLY_FOR_LEADER(403, "스터디장에게만 있는 권한입니다."),
    STUDYGROUP_POST_COMMENT_NOT_FOUND(404, "댓글을 찾을 수 없습니다."),
    STUDYGROUP_POST_COMMENT_NOT_MATCHED(409, "해당 댓글은 해당 스터디 모집글에 존재하지 않습니다. 댓글의 식별자와 스터디 모집글의 식별자를 확인해주세요!"),

    /**
     * 스케쥴 관련 에러 코드
     */
    TIME_SCHEDULE_NOT_ALLOWED(403, "해당 일정은 스터디에 종속되어있습니다. 스터디 일정은 개인이 수정할 수 없습니다!"),
    TIME_SCHEDULE_NOT_FOUND(404, "일정이 존재하지 않습니다."),
    TIME_SCHEDULE_NOT_MATCHED_WITH_MEMBER(409, "해당 일정은 본인이 생성한 것이 아닙니다. 본인이 생성한 일정만 관리할 수 있습니다!"),
    TIME_SCHEDULE_NOT_MATCHED_WITH_STUDYGROUP(409, "해당 일정은 해당 스터디글에 존재하지 않습니다. 일정의 식별자와 스터디의 식별자를 확인해주세요!"),
    TIME_SCHEDULE_NOT_LINKED_WITH_MEMBER(409, "해당 일정은 맴버와 연결되어있지 않습니다. 요청이 잘못된 것 같습니다. 확인해주세요!"),
    TIME_SCHEDULE_NOT_LINKED_WITH_STUDYGROUP(409, "해당 일정은 스터디 그룹과 연결되어있지 않습니다. 요청이 잘못된 것 같습니다. 확인해주세요!"),

    /**
     * 스터디 그룹 신청 관련 에렄 코드
      */
    STUDYGROUP_JOIN_YOU_ARE_STUDYGROUP_LEADER(403, "스터디 리더는 탈퇴가 불가능합니다. 리더를 다른 맴버에게 권한을 이전 한 후에 시도해주세요."),
    STUDYGROUP_JOIN_NOT_FOUND(404, "가입된 스터디를 찾을 수 없습니다."),
    STUDYGROUP_JOIN_EXISTS(409, "이미 가입한 스터디 입니다."),
    STUDYGROUP_JOIN_CANDIDATE_NOT_FOUND(404, "해당 스터디에 신청한 내역이 없습니다."),
    STUDYGROUP_JOIN_CANDIDATE_EXISTS(409, "이미 신청한 스터디 입니다."),

    /**
     * 기타 에러 코드
     */
    INVALID_PROVIDER(400, "지원하지 않는 인증 제공자입니다."),
    INTERNAL_SERVER_ERROR(500, "내부 서버 오류"),
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
