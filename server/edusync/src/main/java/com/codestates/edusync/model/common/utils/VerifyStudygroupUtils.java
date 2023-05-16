package com.codestates.edusync.model.common.utils;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.studygroup.repository.StudygroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VerifyStudygroupUtils implements StudygroupVerifiable {
    private final StudygroupRepository studygroupRepository;

    @Override
    public Studygroup findStudygroup(Long studygroupId) {
        return studygroupRepository.findById(studygroupId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STUDYGROUP_NOT_FOUND));
    }

    @Override
    public boolean verifyMemberLeaderOfStudygroup(String email, Long studygroupId) {
        Studygroup findStudygroup = findStudygroup(studygroupId);
        return findStudygroup.getLeaderMember().getEmail().equals(email);
    }
}