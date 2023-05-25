package com.codestates.edusync.model.study.studygroup.repository;

import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudygroupRepository extends JpaRepository<Studygroup, Long> {

    /**
     * 본인이 리더인 스터디 리스트 조회
     * @param memberId
     * @return
     */
    List<Studygroup> findAllByLeaderMemberId(Long memberId);

    @EntityGraph(attributePaths = {"searchTags", "leaderMember"})
    Optional<Studygroup> findById(Long studygroupId);

    @EntityGraph(attributePaths = "searchTags")
    Page<Studygroup> findAll(Pageable pageable);
}
