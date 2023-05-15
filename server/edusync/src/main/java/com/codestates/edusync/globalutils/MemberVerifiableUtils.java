package com.codestates.edusync.globalutils;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MemberVerifiableUtils implements MemberVerifiable {
    private final MemberRepository memberRepository;

    @Override
    public Member findVerifiedMemberByEmail(String email) {
        Optional<Member> optionalMember =
                memberRepository.findByEmail(email);

        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, String.format("%s 회원을 찾을 수 없습니다.", email)));
        verifyMemberIsActive(findMember);

        return findMember;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findMembersByUUID(List<String> uuids) {
        return memberRepository.findByUuidIn(uuids);
    }

    @Override
    public void verifyMemberIsActive(Member member) {
        if (member.getMemberStatus() != Member.MemberStatus.MEMBER_ACTIVE) {
            throw new BusinessLogicException(ExceptionCode.INACTIVE_MEMBER,
                    String.format("멤버(%s)는 활성화되지 않았습니다. 해당 요청을 처리할 수 없습니다.", member.getEmail()));
        }
    }

    @Override
    public void verifyExistMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS, String.format("%s는 이미 가입한 이메일입니다.", email));
    }

    @Override
    public void verifyExistMemberByNickName(String nickName) {
        Optional<Member> member = memberRepository.findByNickName(nickName);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS, String.format("%s는 이미 사용중인 닉네임입니다.", nickName));
    }

    public Member findVerifyMemberWhoLoggedIn(Authentication authentication) {
        String email = authentication.getPrincipal().toString();

        return findVerifiedMemberByEmail(email);
    }
}
