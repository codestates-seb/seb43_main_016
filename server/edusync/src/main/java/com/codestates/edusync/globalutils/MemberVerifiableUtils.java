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

    /**
     * <h2>email 을 이용해서 회원을 검색하는 메서드</h2>
     * email 로 회원 검색 후 검증하여 리턴해준다.
     * @param email
     * @return 토큰정보와 일치하는 member
     */
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

    /**
     * <h2> uuid를 이용해서 회원을 검색하는 메서드</h2>
     * uuid 리스트로 회원 검색 후 검증하여 리턴해준다.
     * @param uuids
     * @return uuid 리스트와 일치하는 member 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<Member> findMembersByUUID(List<String> uuids) {
        return memberRepository.findByUuidIn(uuids);
    }

    /**
     * <h2> 현재 활동 가능한 회원인지 확인하는 메서드</h2>
     * 회원정보(Member)를 입력받아 현재 활동중인지 확인
     * 삭제, 휴먼 계정이면 오류를 throw
     * @param member
     */
    @Override
    public void verifyMemberIsActive(Member member) {
        if (member.getMemberStatus() != Member.MemberStatus.MEMBER_ACTIVE) {
            throw new BusinessLogicException(ExceptionCode.INACTIVE_MEMBER,
                    String.format("멤버(%s)는 활성화되지 않았습니다. 해당 요청을 처리할 수 없습니다.", member.getEmail()));
        }
    }

    /**
     * <h2>이미 존재하는 이메일인지에 대한 검증</h2>
     * 중복 불가 unique, update 불가 속성인 email을 이용하여, 이미 존재하는 이메일인지를 검증<br>
     * <font color=white>409 Conflict </font>
     * @param email
     */
    @Override
    public void verifyExistMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS, String.format("%s는 이미 가입한 이메일입니다.", email));
    }

    /**
     * <h2>이미 존재하는 닉네임인지에 대한 검증</h2>
     * 중복 불가 unique 속성인 nickName을 이용하여, 이미 존재하는 nickName인지를 검증<br>
     * <font color=white>409 Conflict </font>
     * @param nickName
     */
    @Override
    public void verifyExistMemberByNickName(String nickName) {
        Optional<Member> member = memberRepository.findByNickName(nickName);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS, String.format("%s는 이미 사용중인 닉네임입니다.", nickName));
    }

    /**
     * <h2>현재 로그인 된 사용자의 정보를 리턴해주는 메서드</h2>
     * security context에서 인증 정보를 받아 해당 사용자의 member 객체를 리턴
     * @param authentication
     * @return 접속 중인 Member 의 정보
     */
    public Member findVerifyMemberWhoLoggedIn(Authentication authentication) {
        String email = authentication.getPrincipal().toString();

        return findVerifiedMemberByEmail(email);
    }
}
