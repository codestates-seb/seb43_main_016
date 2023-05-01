package com.codestates.edusync.member.service;

import com.codestates.edusync.localmember.entity.LocalMember;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional // 모두성공 or 모두실패
@Service
public class MemberService {
    private final MemberRepository repository;
    public Member createMember(LocalMember localMember) {
        Member member = new Member();
        member.setLocalMember(localMember);

        Member savedMember = repository.save(member);

        return savedMember;
    }
}
