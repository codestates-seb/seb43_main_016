package com.codestates.edusync.model.member.repository;

import com.codestates.edusync.model.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); // SELECT * FROM member WHERE email = ?1;
    Optional<Member> findById(Long id); // SELECT * FROM member WHERE id = ?1;
    List<Member> findByUuidIn(List<String> uuids); // SELECT * FROM member WHERE uuid IN (?1);
    Optional<Member> findByNickName(String nickName); // SELECT * FROM member WHERE nickName = ?1;
}

