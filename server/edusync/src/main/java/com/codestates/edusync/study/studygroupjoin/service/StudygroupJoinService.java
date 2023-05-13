package com.codestates.edusync.study.studygroupjoin.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.service.MemberService;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import com.codestates.edusync.study.studygroup.service.StudygroupService;
import com.codestates.edusync.study.studygroupjoin.entity.StudygroupJoin;
import com.codestates.edusync.study.studygroupjoin.repository.StudygroupJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class StudygroupJoinService {
    private final StudygroupJoinRepository studygroupJoinRepository;
    private final MemberService memberService;
    private final StudygroupService studygroupService;

    /**
     * 스터디 가입 요청 조회
     * @param studygroupId
     * @param nickName
     * @return
     */
    public StudygroupJoin findStudygroupJoinCandidate(Long studygroupId, String nickName) {
        for (StudygroupJoin sj : studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsFalse(studygroupId)) {
            if (sj.getMember().getNickName().equals(nickName)) return sj;
        }
        return null;
    }

    /**
     * 스터디 가입 멤버 조회
     * @param studygroupId
     * @param nickName
     * @return
     */
    public StudygroupJoin findStudygroupJoin(Long studygroupId, String nickName) {
        for (StudygroupJoin sj : studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsTrue(studygroupId)) {
            if (sj.getMember().getNickName().equals(nickName)) return sj;
        }
        return null;
    }

    /**
     * 스터디 가입 대기 리스트 조회
     * @param studygroupId
     * @return
     */
    public List<StudygroupJoin> findStudygroupJoinCandidateList(Long studygroupId) {
        Member member = memberService.findVerifyMemberWhoLoggedIn();
        Studygroup studygroup = studygroupService.findStudygroup(studygroupId);

        if (member.getId() == studygroup.getLeaderMember().getId()) {
            return studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsFalse(studygroupId);
        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
    }

    /**
     * 스터디 멤버 리스트 조회
     * @param studygroupId
     * @return
     */
    public List<StudygroupJoin> findStudygroupJoinList(Long studygroupId) {
        return studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsTrue(studygroupId);
    }

    /**
     * 스터디 가입 신청
     * @param studygroupId
     */
    public void createStudygroupJoin(Long studygroupId) {
        Member member = memberService.findVerifyMemberWhoLoggedIn();

        if (findStudygroupJoinCandidate(studygroupId, member.getNickName()) == null) {
            StudygroupJoin studygroupJoin = new StudygroupJoin();
            studygroupJoin.setMember(member);
            studygroupJoin.setStudygroup(studygroupService.findStudygroup(studygroupId));
            studygroupJoinRepository.save(studygroupJoin);
        } else throw new BusinessLogicException(ExceptionCode.STUDYGOURP_JOIN_CANDIDATE_EXISTS);
    }

    /**
     * 스터디 가입 신청 철회
     * @param studygroupId
     */
    public void deleteStudygroupJoinCandidate(Long studygroupId) {
        Member member = memberService.findVerifyMemberWhoLoggedIn();
        StudygroupJoin studygroupJoin = null;

        for (StudygroupJoin sj : findStudygroupJoinCandidateList(studygroupId)) {
            if (sj.getMember().getEmail().equals(member.getEmail())) {
                studygroupJoin = sj;
                studygroupJoinRepository.delete(sj);
                break;
            }
        }
        if (studygroupJoin == null) throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_CANDIDATE_NOT_FOUND);
    }

    /**
     * 스터디 탈퇴
     * @param studygourId
     */
    public void deleteStudygroupJoin(Long studygourId) {
        Member member = memberService.findVerifyMemberWhoLoggedIn();
        StudygroupJoin studygroupJoin = null;

        for (StudygroupJoin sj : findStudygroupJoinList(studygourId)) {
            if (sj.getMember().getEmail().equals(member.getEmail())) {
                studygroupJoin = sj;
                studygroupJoinRepository.delete(sj);
                break;
            }
        }
        if (studygroupJoin == null) throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_NOT_FOUND);
    }

    /**
     * 스터디 리더가 가입 승인
     * @param studygroupId
     * @param nickName
     */
    public void approveStudygroupJoin(Long studygroupId, String nickName) {
        Member member = memberService.findVerifyMemberWhoLoggedIn();
        Studygroup studygroup = studygroupService.findStudygroup(studygroupId);

        if (member.getId() != studygroup.getLeaderMember().getId()) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
        } else {
            StudygroupJoin studygroupJoin = findStudygroupJoinCandidate(studygroupId, nickName);
            if (studygroupJoin != null) {
                studygroupJoin.setIsApproved(true);
                studygroupJoinRepository.save(studygroupJoin);
            } else throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_CANDIDATE_NOT_FOUND);
        }
    }

    /**
     * 스터디 리더가 가입 거절
     * @param studygroupId
     * @param nickName
     */
    public void rejectStudygroupJoinCandidate(Long studygroupId, String nickName) {
        Member member = memberService.findVerifyMemberWhoLoggedIn();
        Studygroup studygroup = studygroupService.findStudygroup(studygroupId);

        if (member.getId() == studygroup.getLeaderMember().getId()) {
            StudygroupJoin studygroupJoin = findStudygroupJoinCandidate(studygroupId, nickName);
            if (studygroupJoin != null) {
                studygroupJoinRepository.delete(studygroupJoin);
            } else throw new BusinessLogicException(ExceptionCode.STUDYGROUP_JOIN_CANDIDATE_NOT_FOUND);
        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
    }

    /**
     * 스터디 리더가 멤버 강퇴
     * @param studygroupId
     * @param nickName
     */
    public void deleteStudygroupJoinKick(Long studygroupId, String nickName) {
        Member member = memberService.findVerifyMemberWhoLoggedIn();
        Studygroup studygroup = studygroupService.findStudygroup(studygroupId);

        if (member.getId() == studygroup.getLeaderMember().getId()) {
            StudygroupJoin studygroupJoin = findStudygroupJoin(studygroupId, nickName);
            if (studygroupJoin != null) {
                studygroupJoinRepository.delete(studygroupJoin);
            } else throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
    }
}
