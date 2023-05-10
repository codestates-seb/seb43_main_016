package com.codestates.edusync.study.studygroup.service;

import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.service.MemberService;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import com.codestates.edusync.study.studygroup.repository.StudygroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class StudygroupService {
    private final StudygroupRepository repository;
    private final MemberService memberService;

    /**
     * 스터디 등록
     * 스터디 리더등록을 위한 Member 조회에 대한 의존성 분리 필요해 보임
     * @param studygroup
     * @return
     */
    public Studygroup createStudygruop(Studygroup studygroup) {

        // FIXME: 2023-05-11 멤버 불러오기 실패, 추후 확인 필요
//        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        member = memberService.findVerifiedMember(member.getId());
//        studygroup.setLeaderMember(member);

        Member member = memberService.findVerifiedMember(1L);
        studygroup.setLeaderMember(member);

        return repository.save(studygroup);
    }

    public void updateStudygroup() {

    }

    public void updateStatusStudygroup() {

    }

    public void findStudygroup() {

    }

    public void findStudygroups() {

    }

    public void deleteStudygroup() {

    }

    //public void deleteStudygroup() {}
}
