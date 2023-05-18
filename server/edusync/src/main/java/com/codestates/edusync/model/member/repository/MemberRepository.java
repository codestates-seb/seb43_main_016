package com.codestates.edusync.model.member.repository;

import com.codestates.edusync.model.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findById(Long id);
    List<Member> findByUuidIn(List<String> uuids);
    Optional<Member> findByNickName(String nickName);
}

