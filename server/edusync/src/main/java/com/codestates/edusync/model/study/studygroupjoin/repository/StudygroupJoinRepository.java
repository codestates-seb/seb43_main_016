package com.codestates.edusync.model.study.studygroupjoin.repository;

import com.codestates.edusync.model.study.studygroupjoin.entity.StudygroupJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudygroupJoinRepository extends JpaRepository<StudygroupJoin, Long> {

    /**
     * 사용자가 가입 신청한 스터디 리스트
     * @param memberId
     * @return
     */
    List<StudygroupJoin> findAllByMemberIdAndIsApprovedIsFalse(Long memberId);

    /**
     * 사용자가 가입된 스터디 리스트
     * @param memberId
     * @return
     */
    List<StudygroupJoin> findAllByMemberIdAndIsApprovedIsTrue(Long memberId);

    /**
     * 스터디에 가입 대기 중인 사용자 리스트
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

    // TODO: 2023-05-19 스터디 멤버 수 조회 확인 필요
    /**
     * 스터디에 가입된 멤버 수
     * @param studygroupId
     * @return
     */
    Integer findCountByStudygroupIdAndIsApprovedIsTrue(Long studygroupId);

    StudygroupJoin findByMemberId(Long memberId);
}
