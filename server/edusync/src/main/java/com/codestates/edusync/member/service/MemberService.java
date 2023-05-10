package com.codestates.edusync.member.service;

import com.codestates.edusync.auth.utils.CustomAuthorityUtils;
import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.member.dto.MemberDto;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.repository.MemberRepository;
import com.codestates.edusync.util.VerifyMember;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class MemberService implements VerifyMember {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

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
    public Member updateMember(Member member, Long memberId, String email) {
        sameMemberTest(memberId, email);

        Member findMember = findVerifiedMember(member.getId());
        verifyMemberIsActive(findMember);

        Optional.ofNullable(member.getNickName())
                .ifPresent(name -> findMember.setNickName(name));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> {
                    if (!password.isEmpty()) {
                        findMember.setPassword(password);
                    }
                });
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
        sameMemberTest(memberId, email);
        Member findMember = findVerifiedMember(memberId);
        verifyMemberIsActive(findMember);
        String newEmail = "del_" + memberId + "_" + findMember.getEmail();

        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);
        findMember.setEmail(newEmail);

        memberRepository.save(findMember);
    }

    public Member updateDetail(Long memberId, MemberDto.Detail requestBody, String token){
        Member member = findVerifiedMember(memberId);
        verifyMemberIsActive(member);

        if (requestBody.getWithMe() != null) {
            member.setWithMe(requestBody.getWithMe());
        }
        if (requestBody.getAboutMe() != null) {
            member.setAboutMe(requestBody.getAboutMe());
        }

        return updateMember(member, memberId, token);
    }

    public boolean checkPassword(Long memberId, String password, String email){
        sameMemberTest(memberId, email);

        Member member = memberRepository.findByEmail(email).get();
        return passwordEncoder.matches(password, member.getPassword());
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

    public void sameMemberTest(Long memberId, String email){
        Member findMember = findVerifiedMember(memberId);

        if(email == null || email.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.DUPLICATED_EMAIL, String.format("이메일을 찾을 수 없습니다. 올바른 토큰이 아닐 확률이 높습니다."));
        }else if(!email.equals(findMember.getEmail())){
            throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION, String.format("유저(%s)가 권한을 가지고 있지 않습니다. 사용자(%s) 정보를 수정할 수 없습니다.", email, findMember.getEmail()));
        }
    }

    public void verifyMemberIsActive(Member member) {
        if (member.getMemberStatus() != Member.MemberStatus.MEMBER_ACTIVE) {
            throw new BusinessLogicException(ExceptionCode.INACTIVE_MEMBER,
                    String.format("멤버(%s)는 활성화되지 않았습니다. 해당 요청을 처리할 수 없습니다.", member.getEmail()));
        }
    }

    /**
     * <h2>현재 로그인 된 사용자의 정보를 리턴해주는 메서드</h2>
     * @return 접속 중인 Member 의 정보
     */
    public Member findVerifyMemberWhoLoggedIn() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        return findVerifiedMember(email);
    }

    /**
     * <h2>email 을 이용해서 회원을 검색하는 메서드</h2>
     * email 로 회원 검색 후 검증하여 리턴해준다.
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    public Member findVerifiedMember(String email) {
        Optional<Member> optionalMember =
                memberRepository.findByEmail(email);

        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, String.format("%s번 회원을 찾을 수 없습니다.", email)));
        verifyMemberIsActive(findMember);

        return findMember;
    }
}
