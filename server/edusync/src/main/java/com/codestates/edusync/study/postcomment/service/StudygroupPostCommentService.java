package com.codestates.edusync.study.postcomment.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.service.MemberService;
import com.codestates.edusync.study.postcomment.entity.StudygroupPostComment;
import com.codestates.edusync.study.postcomment.repository.StudygroupPostCommentRepository;
import com.codestates.edusync.study.postcomment.utils.StudygroupPostCommentManager;
import com.codestates.edusync.study.studygroup.service.StudygroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.codestates.edusync.exception.ExceptionCode.*;

@RequiredArgsConstructor
@Service
public class StudygroupPostCommentService implements StudygroupPostCommentManager {
    private final StudygroupPostCommentRepository studygroupPostCommentRepository;
    private final MemberService memberService;
    private final StudygroupService studygroupService;

    @Override
    public StudygroupPostComment createStudygroupPostComment(Long studygroupId,
                                                             StudygroupPostComment comment) {
        Member findMember = memberService.findVerifyMemberWhoLoggedIn();
        comment.setMember(findMember);

        // FIXME: 2023-05-11: 스터디 그룹 머지하면 적용. 지금은 동작 하지 않는다!
//        Studygroup findStudygroup = studygroupService.findVerifyStudygroup(studygroupId);
//        comment.setStudygroup(findStudygroup);

        // 이것도 동작하지않음. 엔티티에 빈값이 있어서 !
        return studygroupPostCommentRepository.save(comment);
    }

    @Override
    public StudygroupPostComment updateStudygroupPostComment(Long studygroupId, Long commentId,
                                                             StudygroupPostComment patchComment) {
        Member findMember = memberService.findVerifyMemberWhoLoggedIn();
        StudygroupPostComment findComment = findVerifyStudygroupPostComment(commentId);
        
        verifyStudygroupPostComment(findMember.getId(), studygroupId, findComment);
        
        Optional.ofNullable(patchComment.getContent())
                .ifPresent(findComment::setContent);
        
        return studygroupPostCommentRepository.save(findComment);
    }

    /**
     * <h2>댓글 조회</h2>
     * 댓글 식별자에 해당하는 댓글이 존재하는지 확인<br>
     * <font color=white>404 Not Found </font> 댓글이 존재하지 않음 !<br>
     * @param commentId
     * @return
     */
    private StudygroupPostComment findVerifyStudygroupPostComment(Long commentId) {
        return studygroupPostCommentRepository.findById(commentId)
                .orElseThrow( () ->
                        new BusinessLogicException(STUDYGROUP_POST_COMMENT_NOT_FOUND)
                );
    }

    /**
     * <h2>댓글의 유효성 검증</h2>
     * <font color=white>400 Not matched</font> 요청받은 스터디 모집글의 식별자와, 코멘트의 스터디 식별자가 일치하지 않음<br>
     * <font color=white>403 Not Allowed</font> 스터디 장이 아니고, 본인이 쓴 글이 아닐 때는 forbidden<br>
     * @param memberId
     * @param studygroupId
     * @param comment
     */
    private void verifyStudygroupPostComment(Long memberId, Long studygroupId,
                                             StudygroupPostComment comment) {
        if( comment.getStudygroup().getId() != studygroupId ) {
            throw new BusinessLogicException(STUDYGROUP_POST_COMMENT_NOT_MATCHED);
        }

        if( comment.getStudygroup().getLeaderMember().getId() != memberId &&
                                  comment.getMember().getId() != memberId ) {
            throw new BusinessLogicException(STUDYGROUP_POST_COMMENT_NOT_ALLOWED);
        }
    }
    
    private void verifyStudygroupMemberLeader(Long memberId,
                                              StudygroupPostComment comment) {
        if( comment.getStudygroup().getLeaderMember().getId() != memberId ) {
            throw new BusinessLogicException(STUDYGROUP_POST_COMMENT_ALLOWED_ONLY_FOR_LEADER);
        }
    }

    @Override
    public List<StudygroupPostComment> getAllStudygroupPostComments(Long studygroupId) {
        return studygroupPostCommentRepository.findAllByStudygroupId(studygroupId);
    }

    @Override
    public void deleteStudygroupPostComment(Long studygroupId, Long commentId) {
        Member findMember = memberService.findVerifyMemberWhoLoggedIn();
        StudygroupPostComment findComment = findVerifyStudygroupPostComment(commentId);

        verifyStudygroupPostComment(findMember.getId(), studygroupId, findComment);

        studygroupPostCommentRepository.delete(findComment);
    }

    @Override
    public void deleteAllStudygroupPostCommentByStudygroupId(Long studygroupId) {
        Member findMember = memberService.findVerifyMemberWhoLoggedIn();
        // FIXME: 2023-05-11 : 머지 후 작업 필요
//        Studygroup findStudygroup = studygroupService.findVerifyStudygroup(studygroupId);
//        
//        verifyStudygroupMemberLeader(findMember.getId(), findStudygroup);
        
        studygroupPostCommentRepository.deleteAllByStudygroupId(studygroupId);
    }

}
