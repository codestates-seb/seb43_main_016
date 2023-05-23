package com.codestates.edusync.model.study.studygroupjoin.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.common.utils.VerifyStudygroupUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.plancalendar.service.CalendarStudygroupService;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.studygroupjoin.entity.StudygroupJoin;
import com.codestates.edusync.model.study.studygroupjoin.repository.StudygroupJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class StudygroupJoinService implements StudygroupJoinManager {
    private final StudygroupJoinRepository studygroupJoinRepository;
    private final VerifyStudygroupUtils verifyStudygroupUtils;
    private final CalendarStudygroupService calendarStudygroupService;
    private final MemberUtils memberUtils;

    private Member getLoginMember(String email) {
        return memberUtils.getLoggedIn(email);
    }

    @Override
    public StudygroupJoin getCandidateByNickName(Long studygroupId, String nickName) {
        List<StudygroupJoin> sjs =
                studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsFalse(studygroupId);

        for (StudygroupJoin sj : sjs) {
            if (sj.getMember().getNickName().equals(nickName)) return sj;
        }
        return null;
    }

    @Override
    public StudygroupJoin getMemberByNickName(Long studygroupId, String nickName) {
        List<StudygroupJoin> sjs =
                studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsTrue(studygroupId);

        for (StudygroupJoin sj : sjs) {
            if (sj.getMember().getNickName().equals(nickName)) return sj;
        }
        return null;
    }

    @Override
    public List<StudygroupJoin> getAllCandidateList(Long studygroupId, String email, boolean isLeader) {
        if (isLeader) verifyStudygroupUtils.studygroupLeaderCheck(email, studygroupId);
        return studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsFalse(studygroupId);
    }

    @Override
    public List<StudygroupJoin> getAllMemberList(Long studygroupId) {
        return studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsTrue(studygroupId);
    }

    @Override
    public void createCandidate(Long studygroupId, String email) {
        StudygroupJoin studygroupJoin = createStudygroupJoinWithVerifyMember(studygroupId, email);
        studygroupJoinRepository.save(studygroupJoin);
    }

    @Override
    public void deleteCandidateSelf(Long studygroupId, String email) {
        delStudygroupJoin(studygroupId, email, false);
    }

    @Override
    public void deleteMemberSelf(Long studygroupId, String email) {
        delStudygroupJoin(studygroupId, email, true);
    }

    /**
     * 스터디 가입신청을 처회하거나 탈퇴할 때, 사용되는 메서드
     * @param studygroupId
     * @param email
     * @param isMember
     */
    public void delStudygroupJoin(Long studygroupId, String email, boolean isMember) {
        Member loginMember = getLoginMember(email);
        StudygroupJoin studygroupJoin = null;
        List<StudygroupJoin> studygroupJoins;

        if (isMember) studygroupJoins = getAllMemberList(studygroupId);
        else studygroupJoins = getAllCandidateList(studygroupId, email, false);

        for (StudygroupJoin sj : studygroupJoins) {
            if (sj.getMember().getEmail().equals(loginMember.getEmail())) {
                studygroupJoin = sj;
                studygroupJoinRepository.delete(sj);
                break;
            }
        }

        if (studygroupJoin == null)
            if (isMember) throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_NOT_FOUND);
            else          throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_CANDIDATE_NOT_FOUND);
    }

    @Override
    public void approveCandidateByNickName(Long studygroupId, String nickName, String email) {
        verifyStudygroupUtils.studygroupLeaderCheck(email, studygroupId);
        StudygroupJoin studygroupJoin = getStudygroupJoin(studygroupId, nickName, false);
        studygroupJoin.setIsApproved(true);
        studygroupJoinRepository.save(studygroupJoin);
    }

    @Override
    public void rejectCandidateByNickName(Long studygroupId, String nickName, String email) {
        verifyStudygroupUtils.studygroupLeaderCheck(email, studygroupId);
        studygroupJoinRepository.delete(getStudygroupJoin(studygroupId, nickName, false));
    }

    @Override
    public void kickOutMemberByNickName(Long studygroupId, String nickName, String email) {
        verifyStudygroupUtils.studygroupLeaderCheck(email, studygroupId);
        studygroupJoinRepository.delete(getStudygroupJoin(studygroupId, nickName, true));

        calendarStudygroupService.deleteTimeScheduleByMember(studygroupId, nickName);
    }

    /**
     * 스터디 리더가 가입 거부하거나, 멤버를 강퇴할 때, 사용되는 메서드
     * @param studygroupId
     * @param nickName
     * @param isMember
     * @return
     */
    private StudygroupJoin getStudygroupJoin(Long studygroupId, String nickName, boolean isMember) {
        StudygroupJoin studygroupJoin;
        if (isMember) {
            studygroupJoin = getMemberByNickName(studygroupId, nickName);
            if (studygroupJoin == null) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        } else {
            studygroupJoin = getCandidateByNickName(studygroupId, nickName);
            if (studygroupJoin == null) throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_CANDIDATE_NOT_FOUND);
        }
        return studygroupJoin;
    }

    @Override
    public List<Studygroup> getMyStudygroupList(String email, boolean isApproved) {
        Member loginMember = getLoginMember(email);
        List<StudygroupJoin> studygroupJoinList;
        if (isApproved)
            studygroupJoinList = studygroupJoinRepository.findAllByMemberIdAndIsApprovedIsTrue(loginMember.getId());
        else
            studygroupJoinList = studygroupJoinRepository.findAllByMemberIdAndIsApprovedIsFalse(loginMember.getId());

        return studygroupJoinList.stream().map(StudygroupJoin::getStudygroup).collect(Collectors.toList());
    }

    @Override
    public int getStudygroupMemberCount(Long studygroupId) {
        return studygroupJoinRepository.countByStudygroupIdAndIsApprovedIsTrue(studygroupId);
    }

    public void createJoinAsLeader(Long studygroupId, String email) {
        StudygroupJoin studygroupJoin = createStudygroupJoinWithVerifyMember(studygroupId, email);
        studygroupJoin.setIsApproved(true);
        studygroupJoinRepository.save(studygroupJoin);
    }

    private StudygroupJoin createStudygroupJoinWithVerifyMember(Long studygroupId, String email) {
        Member loginMember = getLoginMember(email);
        if (getCandidateByNickName(studygroupId, loginMember.getNickName()) != null) {
            throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_CANDIDATE_EXISTS);
        }
        if (getMemberByNickName(studygroupId, loginMember.getNickName())!= null) {
            throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_EXISTS);
        }
        StudygroupJoin studygroupJoin = new StudygroupJoin();
        studygroupJoin.setMember(loginMember);
        studygroupJoin.setStudygroup(verifyStudygroupUtils.findVerifyStudygroup(studygroupId));
        return studygroupJoin;
    }
}
