package com.codestates.edusync.model.common.utils;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.model.study.postcomment.entity.StudygroupPostComment;
import com.codestates.edusync.model.study.postcomment.repository.StudygroupPostCommentRepository;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.codestates.edusync.exception.ExceptionCode.*;

@RequiredArgsConstructor
@Component
public class VerifyStudygroupPostCommentUtils {

    private final StudygroupPostCommentRepository studygroupPostCommentRepository;
    public void verifyStudygroupMemberLeader(Long memberId,
                                             Studygroup studygroup) {
        if( !studygroup.getLeaderMember().getId().equals(memberId) ) {
            throw new BusinessLogicException(STUDYGROUP_POST_COMMENT_ALLOWED_ONLY_FOR_LEADER);
        }
    }

    /**
     * <h2>댓글의 유효성 검증</h2>
     * <font color=white>400 Not matched</font> 요청받은 스터디 모집글의 식별자와, 코멘트의 스터디 식별자가 일치하지 않음<br>
     * <font color=white>403 Not Allowed</font> 스터디 장이 아니고, 본인이 쓴 글이 아닐 때는 forbidden<br>
     * @param memberId
     * @param studygroupId
     * @param comment
     */
    public void verifyStudygroupPostComment(Long memberId, Long studygroupId,
                                             StudygroupPostComment comment) {
        if( !comment.getStudygroup().getId().equals(studygroupId) ) {
            throw new BusinessLogicException(STUDYGROUP_POST_COMMENT_NOT_MATCHED);
        }

        if( !comment.getStudygroup().getLeaderMember().getId().equals(memberId) &&
                !comment.getMember().getId().equals(memberId) ) {
            throw new BusinessLogicException(STUDYGROUP_POST_COMMENT_NOT_ALLOWED);
        }
    }

    /**
     * <h2>댓글 조회</h2>
     * 댓글 식별자에 해당하는 댓글이 존재하는지 확인<br>
     * <font color=white>404 Not Found </font> 댓글이 존재하지 않음 !<br>
     * @param commentId
     * @return
     */
    public StudygroupPostComment findVerifyStudygroupPostComment(Long commentId) {
        return studygroupPostCommentRepository.findById(commentId)
                .orElseThrow( () ->
                        new BusinessLogicException(STUDYGROUP_POST_COMMENT_NOT_FOUND)
                );
    }
}
