package com.codestates.edusync.globalutils;

import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.study.studygroupjoin.entity.StudygroupJoin;
import org.springframework.security.core.Authentication;

/*
  상태 코드에 대한 설명 참조: https://www.rfc-editor.org/rfc/rfc7231
 */
public interface MemberVerifiable {

    Member findVerifiedMemberByEmail(String email);
    Member findVerifiedMemberByEmail(Long memberId);
    Member findVerifiedMemberByNickName(String nickName);
    Member findVerifiedMemberByUuid(Long memberId);

    Member findVerifyMemberWhoLoggedIn(Authentication authentication);
    Member findVerifyMemberWhoLoggedIn();
    void verifyMemberStatusIsActivated(Member member);

    /**
     * <h2>이미 존재하는 회원인지에 대한 검증</h2>
     * 중복 불가 unique 속성인 email 과 nickName 을 이용하여, 이미 존재하는 회원인지를 검증<br>
     * <font color=white>409 Conflict </font>
     * @param email
     * @param nickName
     */
    void verifyExistMember(String email, String nickName);

    /**
     * <h2>스터디 그룹과 맴버 간의 유효성 검사</h2>
     * 해당 맴버가 해당 스터디 그룹의 구성원인지 확인한다.
     * <font color=white>403 Forbidden </font> 해당 맴버가 해당 스터디의 그룹원이 아님<br>
     * <font color=white>404 Studygroup Not Found </font> 스터디 그룹이 존재하지 않음<br>
     * <font color=white>404 Member Not Found </font> 맴버가 존재하지 않음<br>
     * <font color=white>409 Conflict </font> 맴버가 스터디 그룹의 <font color=green>구성원 또는 가입신청자</font>가 아님<br>
     * @param studygroupId
     * @param memberId
     */
    void validateOfStudygroupJoin(Long studygroupId, Long memberId);    // StudygroupJoin Service 에서 처리해주는게 맞을 듯
    void verifyExistStudygroupJoin(Long studygroupId, String nickNameOfMember);     // StudygroupJoin Service 에서 처리해주는게 맞을 듯

    @Deprecated
    StudygroupJoin findVerifiedStudygroupJoin(Long studygroupJoinId);
}
