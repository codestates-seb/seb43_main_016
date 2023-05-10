package com.codestates.edusync.study.postcomment.utils;

import com.codestates.edusync.study.postcomment.entity.StudygroupPostComment;

import java.util.List;

public interface StudygroupPostCommentManager {

    /**
     * <h2>댓글을 생성한다</h2>
     * 해당 스터디 그룹에 댓글을 생성한다.<br>
     * @param studygroupId 스터디 그룹 식별자
     * @param comment      댓글 엔티티
     */
    void createStudygroupPostComment(Long studygroupId, StudygroupPostComment comment);

    /**
     * <h2>댓글을 수정한다</h2>
     * 해당 스터디 그룹의 댓글을 수정한다.<br>
     * @param studygroupId 스터디 그룹 식별자
     * @param comment      댓글 엔티티
     */
    void patchStudygroupPostComment(Long studygroupId, StudygroupPostComment comment);

    /**
     * <h2>댓글들을 전부 조회한다</h2>
     * 해당 스터디 모집글의 댓글을 전부 조회한다.<br>
     * @param studygroupId 스터디 모집글 식별자
     * @return             스터디 모집글의 댓글 리스트
     */
    List<StudygroupPostComment> getAllStudygroupPostComments(Long studygroupId);

    /**
     * <h2>스터디 모질글의 댓글을 삭제한다</h2>
     * 해당 스터디 모집글의 댓글 하나를 삭제한다.<br>
     * @param studygroupId
     * @param studygroupPostCommentId
     */
    void deleteStudygroupPostComment(Long studygroupId, Long studygroupPostCommentId);

    /**
     * <h2>스터디 모집글의 댓글을 전부 삭제한다</h2>
     * 해당 스터디 모집글에 있는 모든 댓글을 삭제한다.<br>
     * @param studygroupId
     */
    void deleteAllStudygroupPostCommentByStudygroupId(Long studygroupId);
}
