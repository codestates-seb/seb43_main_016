package com.codestates.edusync.model.member.service;

import com.codestates.edusync.security.auth.utils.CustomAuthorityUtils;
import com.codestates.edusync.model.common.utils.MemberVerificationService;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.member.repository.MemberRepository;
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
    private final MemberVerificationService memberVerificationService;

    public Member createMember(Member member) {
        memberVerificationService.checkEmailExists(member.getEmail());
        memberVerificationService.checkNicknameExists(member.getNickName());

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
    public Member updateMember(Member member, String email) {

        Member findMember = memberVerificationService.get(email);
        memberVerificationService.checkNicknameExists(member.getNickName());

        Optional.ofNullable(member.getNickName())
                .ifPresent(name -> findMember.setNickName(name));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> {
                    if (!password.isEmpty()) {
                        findMember.setPassword(passwordEncoder.encode(password));
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

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("id").descending()));
    }

    public void deleteMember(String email) {
        Member findMember = memberVerificationService.get(email);
        String newEmail = "del_" + findMember.getId() + "_" + findMember.getEmail();

        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);
        findMember.setEmail(newEmail);

        memberRepository.save(findMember);
    }

    public boolean checkPassword(String password, String email){
        Member member = memberVerificationService.get(email);
        return passwordEncoder.matches(password, member.getPassword());
    }
}
