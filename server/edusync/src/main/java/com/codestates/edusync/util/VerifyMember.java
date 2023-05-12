package com.codestates.edusync.util;

import com.codestates.edusync.member.entity.Member;

public interface VerifyMember {
    Member findVerifiedMember(Long memberId);
    void verifyMemberIsActive(Member member);
    void sameMemberTest(Long memberId, String token);
}
