package com.codestates.edusync.study.postcomment.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.globalutils.VerifyStudygroupPostCommentUtils;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.service.MemberService;
import com.codestates.edusync.study.postcomment.entity.StudygroupPostComment;
import com.codestates.edusync.study.postcomment.repository.StudygroupPostCommentRepository;
import com.codestates.edusync.study.postcomment.utils.StudygroupPostCommentManager;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import com.codestates.edusync.study.studygroup.service.StudygroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.codestates.edusync.exception.ExceptionCode.*;

@Transactional
@RequiredArgsConstructor
@Service
public class StudygroupPostCommentService implements StudygroupPostCommentManager {
    private final StudygroupPostCommentRepository studygroupPostCommentRepository;
    private final VerifyStudygroupPostCommentUtils verifyStudygroupPostCommentUtils;
    private final VerifyStudygroupUtils verifyStudygroupUtils;
    private final VerifyMemberUtils verifyMemberUtils;

    @Override
    public StudygroupPostComment createStudygroupPostComment(Long studygroupId,
                                                             StudygroupPostComment comment) {
        Member findMember = verifyMemberUtils.findVerifyMemberWhoLoggedIn();
        comment.setMember(findMember);

        Studygroup findStudygroup = verifyStudygroupUtils.findStudygroup(studygroupId);
        comment.setStudygroup(findStudygroup);

        return studygroupPostCommentRepository.save(comment);
    }

    @Override
    public StudygroupPostComment updateStudygroupPostComment(Long studygroupId, Long commentId,
                                                             StudygroupPostComment patchComment) {
        Member findMember = verifyMemberUtils.findVerifyMemberWhoLoggedIn();
        StudygroupPostComment findComment = verifyStudygroupPostCommentUtils.findVerifyStudygroupPostComment(commentId);
        
        verifyStudygroupPostCommentUtils.verifyStudygroupPostComment(findMember.getId(), studygroupId, findComment);
        
        Optional.ofNullable(patchComment.getContent())
                .ifPresent(findComment::setContent);
        
        return studygroupPostCommentRepository.save(findComment);
    }

    @Override
    public List<StudygroupPostComment> getAllStudygroupPostComments(Long studygroupId) {
        return studygroupPostCommentRepository.findAllByStudygroupId(studygroupId);
    }

    @Override
    public void deleteStudygroupPostComment(Long studygroupId, Long commentId) {
        Member findMember = verifyMemberUtils.findVerifyMemberWhoLoggedIn();
        StudygroupPostComment findComment = verifyStudygroupPostCommentUtils.findVerifyStudygroupPostComment(commentId);

        verifyStudygroupPostCommentUtils.verifyStudygroupPostComment(findMember.getId(), studygroupId, findComment);

        studygroupPostCommentRepository.delete(findComment);
    }

    @Override
    public void deleteAllStudygroupPostCommentByStudygroupId(Long studygroupId) {
        Member findMember = verifyMemberUtils.findVerifyMemberWhoLoggedIn();
        Studygroup findStudygroup = verifyStudygroupUtils.findStudygroup(studygroupId);

        verifyStudygroupPostCommentUtils.verifyStudygroupMemberLeader(findMember.getId(), findStudygroup);
        
        studygroupPostCommentRepository.deleteAllByStudygroupId(studygroupId);
    }
}
