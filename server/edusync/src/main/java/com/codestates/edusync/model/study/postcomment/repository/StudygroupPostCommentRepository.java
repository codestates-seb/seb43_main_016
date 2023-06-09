package com.codestates.edusync.model.study.postcomment.repository;

import com.codestates.edusync.model.study.postcomment.entity.StudygroupPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudygroupPostCommentRepository extends JpaRepository<StudygroupPostComment, Long> {

    List<StudygroupPostComment> findAllByStudygroupId(Long studygroupId);

    void deleteAllByStudygroupId(Long studygroupId);
}
