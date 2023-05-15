package com.codestates.edusync.study.postcomment.service;

import com.codestates.edusync.globalutils.VerifyStudygroupPostCommentUtils;
import com.codestates.edusync.globalutils.VerifyStudygroupUtils;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.study.postcomment.entity.StudygroupPostComment;
import com.codestates.edusync.study.postcomment.repository.StudygroupPostCommentRepository;
import com.codestates.edusync.study.postcomment.utils.StudygroupPostCommentManager;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class StudygroupPostCommentService implements StudygroupPostCommentManager {
    private final StudygroupPostCommentRepository studygroupPostCommentRepository;
    private final VerifyStudygroupPostCommentUtils verifyStudygroupPostCommentUtils;
    private final VerifyStudygroupUtils verifyStudygroupUtils;

    @Override
    public StudygroupPostComment createStudygroupPostComment(Long studygroupId,
                                                             StudygroupPostComment comment,
                                                             Member loginMember) {
        comment.setMember(loginMember);

        Studygroup findStudygroup = verifyStudygroupUtils.findStudygroup(studygroupId);
        comment.setStudygroup(findStudygroup);

        return studygroupPostCommentRepository.save(comment);
    }

    @Override
    public StudygroupPostComment updateStudygroupPostComment(Long studygroupId, Long commentId,
                                                             StudygroupPostComment patchComment,
                                                             Member loginMember) {
        StudygroupPostComment findComment = verifyStudygroupPostCommentUtils.findVerifyStudygroupPostComment(commentId);
        
        verifyStudygroupPostCommentUtils.verifyStudygroupPostComment(loginMember.getId(), studygroupId, findComment);
        
        Optional.ofNullable(patchComment.getContent())
                .ifPresent(findComment::setContent);
        
        return studygroupPostCommentRepository.save(findComment);
    }

    @Override
    public List<StudygroupPostComment> getAllStudygroupPostComments(Long studygroupId) {
        return studygroupPostCommentRepository.findAllByStudygroupId(studygroupId);
    }

    @Override
    public void deleteStudygroupPostComment(Long studygroupId, Long commentId, Member loginMember) {
        StudygroupPostComment findComment = verifyStudygroupPostCommentUtils.findVerifyStudygroupPostComment(commentId);

        verifyStudygroupPostCommentUtils.verifyStudygroupPostComment(loginMember.getId(), studygroupId, findComment);

        studygroupPostCommentRepository.delete(findComment);
    }

    @Override
    public void deleteAllStudygroupPostCommentByStudygroupId(Long studygroupId, Member loginMember) {
        Studygroup findStudygroup = verifyStudygroupUtils.findStudygroup(studygroupId);

        verifyStudygroupPostCommentUtils.verifyStudygroupMemberLeader(loginMember.getId(), findStudygroup);
        
        studygroupPostCommentRepository.deleteAllByStudygroupId(studygroupId);
    }
}
