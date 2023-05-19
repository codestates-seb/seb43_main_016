package com.codestates.edusync.model.study.studygroupjoin.repository;

import com.codestates.edusync.model.study.studygroupjoin.entity.StudygroupJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudygroupJoinRepository extends JpaRepository<StudygroupJoin, Long> {
    /**
     * 스터디 가입 대기 리스트
     * @param studygroupId
     * @return
     */
    List<StudygroupJoin> findAllByStudygroupIdAndIsApprovedIsFalse(Long studygroupId);

    /**
     * 스터디 멤버 리스트
     * @param studygroupId
     * @return
     */
    List<StudygroupJoin> findAllByStudygroupIdAndIsApprovedIsTrue(Long studygroupId);

    StudygroupJoin findByMemberId(Long memberId);
}
