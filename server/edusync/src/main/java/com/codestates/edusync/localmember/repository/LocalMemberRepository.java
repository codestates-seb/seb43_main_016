package com.codestates.edusync.localmember.repository;

import com.codestates.edusync.localmember.entity.LocalMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalMemberRepository extends JpaRepository<LocalMember, Long> {
    Optional<LocalMember> findByEmail(String email); // JPA가 자동으로 아래 쿼리문을 수행하는 메서드로 만들어줌
//    SELECT *
//    FROM local_member
//    WHERE email = ?1;
}
