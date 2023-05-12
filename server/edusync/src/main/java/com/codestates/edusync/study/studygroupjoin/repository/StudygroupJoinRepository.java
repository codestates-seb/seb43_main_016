package com.codestates.edusync.study.studygroupjoin.repository;

import com.codestates.edusync.study.studygroupjoin.entity.StudygroupJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudygroupJoinRepository extends JpaRepository<StudygroupJoin, Long> {
    List<StudygroupJoin> findAllByStudygroupIdAndIsApprovedIsFalse(Long StudygroupId);
    List<StudygroupJoin> findAllByStudygroupIdAndIsApprovedIsTrue(Long StudygroupId);
}
