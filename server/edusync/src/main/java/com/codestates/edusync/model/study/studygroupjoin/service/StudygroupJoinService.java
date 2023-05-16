package com.codestates.edusync.model.study.studygroupjoin.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.model.common.service.VerifyVerifyStudygroupUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.studygroupjoin.entity.StudygroupJoin;
import com.codestates.edusync.model.study.studygroupjoin.repository.StudygroupJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class StudygroupJoinService implements StudygroupJoinManager {
    private final StudygroupJoinRepository studygroupJoinRepository;
    private final VerifyVerifyStudygroupUtils verifyStudygroupUtils;

    @Override
    public StudygroupJoin getCandidateByNickName(Long studygroupId, String nickName) {
        for (StudygroupJoin sj : studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsFalse(studygroupId)) {
            if (sj.getMember().getNickName().equals(nickName)) return sj;
        }
        return null;
    }

    @Override
    public StudygroupJoin getMemberByNickName(Long studygroupId, String nickName) {
        for (StudygroupJoin sj : studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsTrue(studygroupId)) {
            if (sj.getMember().getNickName().equals(nickName)) return sj;
        }
        return null;
    }

    @Override
    public List<StudygroupJoin> getAllCandidateList(Long studygroupId, Member loginMember) {
        Studygroup studygroup = verifyStudygroupUtils.findStudygroup(studygroupId);

        if (loginMember.getId() != studygroup.getLeaderMember().getId()) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
        } else return studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsFalse(studygroupId);
    }

    @Override
    public List<StudygroupJoin> getAllMemberList(Long studygroupId) {
        return studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsTrue(studygroupId);
    }

    @Override
    public void createCandidate(Long studygroupId, Member loginMember) {
        if (getCandidateByNickName(studygroupId, loginMember.getNickName()) != null) {
            throw new BusinessLogicException(ExceptionCode.STUDYGOURP_JOIN_CANDIDATE_EXISTS);
        } else {
            StudygroupJoin studygroupJoin = new StudygroupJoin();
            studygroupJoin.setMember(loginMember);
            studygroupJoin.setStudygroup(verifyStudygroupUtils.findStudygroup(studygroupId));
            studygroupJoinRepository.save(studygroupJoin);
        }
    }

    @Override
    public void deleteCandidateSelf(Long studygroupId, Member loginMember) {
        StudygroupJoin studygroupJoin = null;

        for (StudygroupJoin sj : getAllCandidateList(studygroupId, loginMember)) {
            if (sj.getMember().getEmail().equals(loginMember.getEmail())) {
                studygroupJoin = sj;
                studygroupJoinRepository.delete(sj);
                break;
            }
        }
        if (studygroupJoin == null) throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_CANDIDATE_NOT_FOUND);
    }

    @Override
    public void deleteMemberSelf(Long studygroupId, Member loginMember) {
        StudygroupJoin studygroupJoin = null;

        for (StudygroupJoin sj : getAllMemberList(studygroupId)) {
            if (sj.getMember().getEmail().equals(loginMember.getEmail())) {
                studygroupJoin = sj;
                studygroupJoinRepository.delete(sj);
                break;
            }
        }
        if (studygroupJoin == null) throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_NOT_FOUND);
    }

    @Override
    public void approveCandidateByNickName(Long studygroupId, String nickName, Member loginMember) {
        Studygroup studygroup = verifyStudygroupUtils.findStudygroup(studygroupId);

        if (loginMember.getId() != studygroup.getLeaderMember().getId()) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
        } else {
            StudygroupJoin studygroupJoin = getCandidateByNickName(studygroupId, nickName);
            if (studygroupJoin != null) {
                studygroupJoin.setIsApproved(true);
                studygroupJoinRepository.save(studygroupJoin);
            } else throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_CANDIDATE_NOT_FOUND);
        }
    }

    @Override
    public void rejectCandidateByNickName(Long studygroupId, String nickName, Member loginMember) {
        Studygroup studygroup = verifyStudygroupUtils.findStudygroup(studygroupId);

        if (loginMember.getId() == studygroup.getLeaderMember().getId()) {
            StudygroupJoin studygroupJoin = getCandidateByNickName(studygroupId, nickName);
            if (studygroupJoin != null) {
                studygroupJoinRepository.delete(studygroupJoin);
            } else throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_CANDIDATE_NOT_FOUND);
        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
    }

    @Override
    public void kickOutMemberByNickName(Long studygroupId, String nickName, Member loginMember) {
        Studygroup studygroup = verifyStudygroupUtils.findStudygroup(studygroupId);

        if (loginMember.getId() == studygroup.getLeaderMember().getId()) {
            StudygroupJoin studygroupJoin = getMemberByNickName(studygroupId, nickName);
            if (studygroupJoin != null) {
                studygroupJoinRepository.delete(studygroupJoin);
            } else throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
    }
}
