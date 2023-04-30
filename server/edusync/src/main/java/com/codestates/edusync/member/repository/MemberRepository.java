package com.codestates.edusync.member.repository;

import com.codestates.edusync.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLocalMember_Email(String email); // Member의 필드값 중 localMember의 email을 기준으로 찾기 (JPA가 쿼리문 자동 구현)
//    SELECT m.*
//    FROM member m
//    JOIN local_member lm ON m.local_member_id = lm.id
//    WHERE lm.email = ?1;
}

