package com.codestates.edusync.model.study.postcomment.service;

import com.codestates.edusync.model.common.utils.VerifyStudygroupPostCommentUtils;
import com.codestates.edusync.model.common.utils.VerifyVerifyStudygroupUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.postcomment.entity.StudygroupPostComment;
import com.codestates.edusync.model.study.postcomment.repository.StudygroupPostCommentRepository;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
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
    private final VerifyVerifyStudygroupUtils verifyStudygroupUtils;

    @Override
    public StudygroupPostComment create(Long studygroupId,
                                        StudygroupPostComment comment,
                                        Member loginMember) {
        comment.setMember(loginMember);

        Studygroup findStudygroup = verifyStudygroupUtils.findStudygroup(studygroupId);
        comment.setStudygroup(findStudygroup);

        return studygroupPostCommentRepository.save(comment);
    }

    @Override
    public StudygroupPostComment update(Long studygroupId, Long commentId,
                                        StudygroupPostComment patchComment,
                                        Member loginMember) {
        StudygroupPostComment findComment = verifyStudygroupPostCommentUtils.findVerifyStudygroupPostComment(commentId);
        
        verifyStudygroupPostCommentUtils.verifyStudygroupPostComment(loginMember.getId(), studygroupId, findComment);
        
        Optional.ofNullable(patchComment.getContent())
                .ifPresent(findComment::setContent);
        
        return studygroupPostCommentRepository.save(findComment);
    }

    @Override
    public List<StudygroupPostComment> getAll(Long studygroupId) {
        return studygroupPostCommentRepository.findAllByStudygroupId(studygroupId);
    }

    @Override
    public void delete(Long studygroupId, Long commentId, Member loginMember) {
        StudygroupPostComment findComment = verifyStudygroupPostCommentUtils.findVerifyStudygroupPostComment(commentId);

        verifyStudygroupPostCommentUtils.verifyStudygroupPostComment(loginMember.getId(), studygroupId, findComment);

        studygroupPostCommentRepository.delete(findComment);
    }

    @Override
    public void deleteAllByStudygroupId(Long studygroupId, Member loginMember) {
        Studygroup findStudygroup = verifyStudygroupUtils.findStudygroup(studygroupId);

        verifyStudygroupPostCommentUtils.verifyStudygroupMemberLeader(loginMember.getId(), findStudygroup);
        
        studygroupPostCommentRepository.deleteAllByStudygroupId(studygroupId);
    }
}
