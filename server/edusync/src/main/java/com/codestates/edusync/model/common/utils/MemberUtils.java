package com.codestates.edusync.model.common.utils;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Component
public class MemberUtils implements MemberVerificationManager {
    private final MemberRepository memberRepository;

    @Override
    public Member get(String email) {
        Optional<Member> optionalMember =
                memberRepository.findByEmail(email);

        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, String.format("%s 회원을 찾을 수 없습니다.", email)));
        checkIsActive(findMember);

        return findMember;
    }

    @Override
    public Member getById(Long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);

        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, "회원을 찾을 수 없습니다."));
        checkIsActive(findMember);

        return findMember;
    }

    @Override
    public List<Member> getList(List<String> uuids) {
        return memberRepository.findByUuidIn(uuids);
    }

    @Override
    public void checkIsActive(Member member) {
        if (member.getMemberStatus() != Member.MemberStatus.MEMBER_ACTIVE) {
            throw new BusinessLogicException(ExceptionCode.INACTIVE_MEMBER,
                    String.format("멤버(%s)는 활성화되지 않았습니다. 해당 요청을 처리할 수 없습니다.", member.getEmail()));
        }
    }

    @Override
    public boolean isActive(Member member) {
        if (member.getMemberStatus().equals(Member.MemberStatus.MEMBER_ACTIVE)) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void checkEmailExists(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS_EMAIL, String.format("%s는 이미 가입한 이메일입니다.", email));
    }

    @Override
    public void checkNicknameExists(String nickName) {
        Optional<Member> member = memberRepository.findByNickName(nickName);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS_NICKNAME, String.format("%s는 이미 사용중인 닉네임입니다.", nickName));
    }

    public Member getLoggedIn(String email) {
        return get(email);
    }

    public Member getLoggedInWithAuthenticationCheck(Authentication authentication) {
        if( authentication == null ) {
            throw new BusinessLogicException(ExceptionCode.AUTHENTICATION_NOT_NULL_ALLOWED);
        }

        return getLoggedIn(authentication.getPrincipal().toString());
    }
}
