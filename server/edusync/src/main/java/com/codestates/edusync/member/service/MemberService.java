package com.codestates.edusync.member.service;

import com.codestates.edusync.auth.utils.CustomAuthorityUtils;
import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.member.dto.MemberDto;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.repository.MemberRepository;
import com.codestates.edusync.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final JwtUtil jwtUtil;

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
        member.setAboutMe(""); // FE 요청으로 추가 (null -> 빈문자열)
        member.setWithMe("");

        if (member.getProfileImage() == null || member.getProfileImage().isEmpty()) {
            member.setProfileImage("https://avatars.githubusercontent.com/u/120456261?v=4");
        }

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Member member, Long memberId, String token) {
        verifyMemberIsActive(member);
        sameMemberTest(memberId, token);

        Member findMember = findVerifiedMember(member.getId());

        Optional.ofNullable(member.getNickName())
                .ifPresent(name -> findMember.setNickName(name));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findMember.setPassword(password));
        Optional.ofNullable(member.getProfileImage())
                .ifPresent(image -> findMember.setProfileImage(image));
        Optional.ofNullable(member.getWithMe())
                .ifPresent(withMe -> findMember.setWithMe(withMe));
        Optional.ofNullable(member.getAboutMe())
                .ifPresent(aboutMe -> findMember.setAboutMe(aboutMe));
        return memberRepository.save(findMember);
    }

    @Transactional(readOnly = true)
    public Member findMember(Long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("id").descending()));
    }

    public void deleteMember(Long memberId, String email) {
        sameMemberTest2(memberId, email);
        Member findMember = findVerifiedMember(memberId);
        verifyMemberIsActive(findMember);
        String newEmail = "del_" + memberId + "_" + findMember.getEmail();

        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);
        findMember.setEmail(newEmail);

        memberRepository.save(findMember);
    }

    public Member updateDetail(Long memberId, MemberDto.PostDetail requestBody, String token){
        Member member = findVerifiedMember(memberId);
        verifyMemberIsActive(member);
        member.setWithMe(requestBody.getWithMe());
        member.setAboutMe(requestBody.getAboutMe());

        return updateMember(member, memberId, token);
    }

    @Transactional(readOnly = true)
    public Member findVerifiedMember(Long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);

        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, String.format("%s번 회원을 찾을 수 없습니다.", memberId)));
        verifyMemberIsActive(findMember);

        return findMember;
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS, String.format("%s는 이미 가입한 이메일입니다.", email));
    }

    public void sameMemberTest(Long memberId, String token){
        String email = jwtUtil.extractEmailFromToken(token);
        Member findMember = findVerifiedMember(memberId);

        if(email == null || email == ""){
            throw new BusinessLogicException(ExceptionCode.DUPLICATED_EMAIL, String.format("이메일을 찾을 수 없습니다. 올바른 토큰이 아닐 확률이 높습니다."));
        }else if(!email.equals(findMember.getEmail())){
            throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION, String.format("유저(%s)가 권한을 가지고 있지 않습니다. 사용자(%s) 정보를 수정할 수 없습니다.", email, findMember.getEmail()));
        }
    }

    public void sameMemberTest2(Long memberId, String email){
        Member findMember = memberRepository.findByEmail(email).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, String.format("이메일(%s)에 해당하는 회원을 찾을 수 없습니다.", email))); // 어차피 인증객체 가져오는거라 불가능한 경우의 수다. 하지만 더블 체크용으로 작성

        if(!email.equals(findMember.getEmail())){
            throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION, String.format("유저(%s)가 권한을 가지고 있지 않습니다. 사용자(%s) 정보를 수정할 수 없습니다.", email, findMember.getEmail()));
        }
    }

    private void verifyMemberIsActive(Member member) {
        if (member.getMemberStatus() != Member.MemberStatus.MEMBER_ACTIVE) {
            throw new BusinessLogicException(ExceptionCode.INACTIVE_MEMBER,
                    String.format("멤버(%s)는 활성화되지 않았습니다. 해당 요청을 처리할 수 없습니다.", member.getEmail()));
        }
    }

}
