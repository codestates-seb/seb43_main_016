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
    private static MemberService memberService;
    private static StudygroupService studygroupService;


    /**
     * 스터디 가입 신청
     * @param studygroupId
     */
    public void createStudygroupJoin(Long studygroupId) {
        StudygroupJoin studygroupJoin = new StudygroupJoin();
        studygroupJoin.setMember(memberService.findVerifyMemberWhoLoggedIn());
        studygroupJoin.setStudygroup(studygroupService.findStudygroup(studygroupId));
        studygroupJoinRepository.save(studygroupJoin);
    }

    // 스터디 리더가 가입 승인
    public void approveStudygroupJoin(Long studygroupId, String nickName) {
        Member member = memberService.findVerifyMemberWhoLoggedIn();
        Studygroup studygroup = studygroupService.findStudygroup(studygroupId);

        if (member.getId() != studygroup.getLeaderMember().getId()) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
        } else {
            StudygroupJoin studygroupJoin = findStudygroupJoinCandidate(studygroupId, nickName);
            studygroupJoin.setIsApproved(true);
            studygroupJoinRepository.save(studygroupJoin);
        }
    }

    /**
     * 스터디 가입 요청 조회
     * @return StudygroupJoin
     */
    public StudygroupJoin findStudygroupJoinCandidate(Long studygroupId, String nickName) {
        StudygroupJoin studygroupJoin = new StudygroupJoin();
        for (StudygroupJoin sj : studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsFalse(studygroupId)) {
            if (sj.getMember().getNickName().equals(nickName)) {
                studygroupJoin = sj;
                break;
            }
        }
        if (studygroupJoin == null) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        return studygroupJoin;
    }

    /**
     * 스터디 가입 대기자 리스트 조회
     * @param studygroupId
     * @return
     */
    public List<StudygroupJoin> findStudygroupJoinsCandidateList(Long studygroupId) {
        return studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsFalse(studygroupId);
    }

    /**
     * 스터디 가입된 멤버 리스트 조회
     * @param studygroupId
     * @return
     */
    public List<StudygroupJoin> findStudygroupJoinsList(Long studygroupId) {
        return studygroupJoinRepository.findAllByStudygroupIdAndIsApprovedIsTrue(studygroupId);
    }

    /**
     * 스터디 리더가 가입 거부
     * @param studygroupId
     * @param nickName
     */
    public void deleteStudygroupJoinCandidate(Long studygroupId, String nickName) {
        Member member = memberService.findVerifyMemberWhoLoggedIn();
        Studygroup studygroup = studygroupService.findStudygroup(studygroupId);

        if (member.getId() != studygroup.getLeaderMember().getId()) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
        } else {
            studygroupJoinRepository.delete(findStudygroupJoinCandidate(studygroupId, nickName));
        }
    }

    /**
     * 본인이 스터디 가입 신청 철회
     * @param studygroupId
     */
    public void deleteSelfStudygroupJoinCandidate(Long studygroupId) {
        Member member = memberService.findVerifyMemberWhoLoggedIn();

        StudygroupJoin studygroupJoin = new StudygroupJoin();
        for (StudygroupJoin sj : findStudygroupJoinsCandidateList(studygroupId)) {
            if (sj.getMember().getEmail().equals(member.getEmail())) {
                studygroupJoin = sj;
                studygroupJoinRepository.delete(sj);
            }
        }
        if (studygroupJoin == null) throw new BusinessLogicException(ExceptionCode.STUDYGROUP_NOT_FOUND);
    }
}
