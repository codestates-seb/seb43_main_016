package com.codestates.edusync.model.study.postcomment.service;

import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.postcomment.entity.StudygroupPostComment;

import java.util.List;

public interface StudygroupPostCommentManager {

    /**
     * <h2>댓글을 생성한다</h2>
     * 해당 스터디 그룹에 댓글을 생성한다.<br>
     * @param studygroupId 스터디 그룹 식별자
     * @param comment      댓글
     * @param loginMember  로그인 중인 맴버
     */
    StudygroupPostComment create(Long studygroupId, StudygroupPostComment comment, String email);

    /**
     * <h2>댓글을 수정한다</h2>
     * 해당 스터디 그룹의 댓글을 수정한다.<br>
     * @param studygroupId 스터디 그룹 식별자
     * @param commentId    댓글 식별자
     * @param comment      댓글
     * @param loginMember  로그인 중인 맴버
     */
    StudygroupPostComment update(Long studygroupId, Long commentId, StudygroupPostComment comment, String email);

    /**
     * <h2>댓글들을 전부 조회한다</h2>
     * 해당 스터디 모집글의 댓글을 전부 조회한다.<br>
     * @param studygroupId 스터디 모집글 식별자
     * @return             스터디 모집글의 댓글 리스트
     */
    List<StudygroupPostComment> getAll(Long studygroupId);

    /**
     * <h2>스터디 모질글의 댓글을 삭제한다</h2>
     * 해당 스터디 모집글의 댓글 하나를 삭제한다.<br>
     * @param studygroupId
     * @param studygroupPostCommentId
     * @param loginMember             로그인 중인 맴버
     */
    void delete(Long studygroupId, Long studygroupPostCommentId, String email);

    /**
     * <h2>스터디 모집글의 댓글을 전부 삭제한다</h2>
     * 해당 스터디 모집글에 있는 모든 댓글을 삭제한다.<br>
     * @param studygroupId
     * @param loginMember  로그인 중인 맴버
     */
    void deleteAllByStudygroupId(Long studygroupId, String email);
}
