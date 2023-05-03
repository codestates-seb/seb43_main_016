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

@AllArgsConstructor // 생성자 땜빵용
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final JwtUtil jwtUtil;

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());

        String encryptedPassword = passwordEncoder.encode(member.getPassword()); // Password 단방향 암호화
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        if (member.getProfileImage() == null || member.getProfileImage().isEmpty()) { // 기본 이미지 등록
            member.setProfileImage("https://avatars.githubusercontent.com/u/120456261?v=4");
        }

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Member member, Long memberId, String token) {
        sameMemberTest(memberId, token); // 변경하려는 회원이 맞는지 확인

        Member findMember = findVerifiedMember(member.getId());

        Optional.ofNullable(member.getNickName())
                .ifPresent(name -> findMember.setNickName(name));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findMember.setPassword(password));
        Optional.ofNullable(member.getProfileImage())
                .ifPresent(image -> findMember.setProfileImage(image));
        Optional.ofNullable(member.getLocation())
                .ifPresent(location -> findMember.setLocation(location));
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
        sameMemberTest2(memberId, email); // authentication.getName()이 이메일 가져오는거다.
        Member findMember = findVerifiedMember(memberId);

        memberRepository.delete(findMember);
    }

    public Member updateDetail(Long memberId, MemberDto.PostDetail requestBody, String token){
        Member member = findVerifiedMember(memberId);
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
}
