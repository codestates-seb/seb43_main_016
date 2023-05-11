package com.codestates.edusync.member.repository;

import com.codestates.edusync.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findByUuidIn(List<String> uuids);
}

