package com.codestates.edusync.globalutils;

import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.study.studygroupjoin.entity.StudygroupJoin;
import org.springframework.security.core.Authentication;

import java.util.List;

/*
  상태 코드에 대한 설명 참조: https://www.rfc-editor.org/rfc/rfc7231
 */
public interface MemberVerifiable {
    Member findVerifiedMemberByEmail(String email);
    List<Member> findMembersByUUID(List<String> uuids);
    void verifyMemberIsActive(Member member);
    void verifyExistMemberByEmail(String email);
    void verifyExistMemberByNickName(String nickname);

    Member findVerifyMemberWhoLoggedIn(Authentication authentication);
}
