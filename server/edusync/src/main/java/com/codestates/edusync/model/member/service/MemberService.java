package com.codestates.edusync.model.member.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.security.auth.utils.CustomAuthorityUtils;
import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.member.repository.MemberRepository;
import com.codestates.edusync.security.auth.utils.ErrorResponder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberUtils memberUtils;

    public Member createMember(Member member) {
        memberUtils.checkEmailExists(member.getEmail());
        memberUtils.checkNicknameExists(member.getNickName());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
        member.setAboutMe("");
        member.setWithMe("");

        if (member.getProfileImage() == null || member.getProfileImage().isEmpty()) {
            member.setProfileImage("https://avatars.githubusercontent.com/u/120456261?v=4");
        }

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Member member, String email) {

        Member findMember = memberUtils.get(email);
        memberUtils.checkNicknameExists(member.getNickName());

        Optional.ofNullable(member.getNickName())
                .ifPresent(name -> {
                    if (!name.isEmpty()) {
                        findMember.setNickName(name);
                    }
                });
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
        Member findMember = memberUtils.get(email);

        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);

        memberRepository.save(findMember);
    }

    public boolean checkPassword(String password, String email){
        Member member = memberUtils.get(email);
        return passwordEncoder.matches(password, member.getPassword());
    }

    public Map<String, String> checkProvider(String email){
        Member member = memberUtils.get(email);

        String provider;

        if(member.getProvider()==Member.Provider.LOCAL){
            provider = "LOCAL";
        }else if(member.getProvider()==Member.Provider.GOOGLE){
            provider = "GOOGLE";
        }else if(member.getProvider()==Member.Provider.NAVER){
            provider = "NAVER";
        }else if(member.getProvider()==Member.Provider.KAKAO){
            provider = "KAKAO";
        }else{
            provider = "Error";
        }

        Map<String, String> response = new HashMap<>();
        response.put("provider", provider);

        return response;
    }

    public Member reactive(String email, String password){
        Optional<Member> optionalMember =
                memberRepository.findByEmail(email);

        Member member =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, String.format("%s 회원을 찾을 수 없습니다.", email)));


        if(passwordEncoder.matches(password, member.getPassword()) && !member.getMemberStatus().equals(Member.MemberStatus.MEMBER_ACTIVE)){
            member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
            memberRepository.save(member); // 이게 없어도 JPA Dirty Checking 기능으로 인해 변경사항이 db에 자동으로 저장된다.
        }else if(member.getMemberStatus().equals(Member.MemberStatus.MEMBER_ACTIVE)){
            throw new BusinessLogicException(ExceptionCode.MEMBER_ALREADY_ACTIVE);
        }else if(!passwordEncoder.matches(password, member.getPassword())){
            throw new BusinessLogicException(ExceptionCode.PASSWORD_ERROR);
        }

        return member;
    }
}
