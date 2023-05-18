package com.codestates.edusync.model.common.utils;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.studygroup.repository.StudygroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VerifyStudygroupUtils implements VerifyStudygroupManager {
    private final StudygroupRepository studygroupRepository;
    private final MemberUtils memberUtils;

    @Override
    public Studygroup findVerifyStudygroup(Long studygroupId) {
        return studygroupRepository.findById(studygroupId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STUDYGROUP_NOT_FOUND));
    }

    @Override
    public boolean isMemberLeaderOfStudygroup(String email, Long studygroupId) {
        Studygroup findStudygroup = findVerifyStudygroup(studygroupId);
        return findStudygroup.getLeaderMember().getEmail().equals(email);
    }

    @Override
    public void studygroupLeaderCheck(String email, Long studygroupId) {
        Member loginMember = memberUtils.getLoggedIn(email);
        Studygroup studygroup = findVerifyStudygroup(studygroupId);

        if (loginMember.getId() != studygroup.getLeaderMember().getId()) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
        }
    }
}