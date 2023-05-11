package com.codestates.edusync.study.studygroup.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.service.MemberService;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import com.codestates.edusync.study.studygroup.repository.StudygroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

        // FIXME: 2023-05-11 스터디 리더를 등록하기 위한 멤버 불러오기 실패, 추후 확인 필요
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        member = memberService.findVerifiedMember(member.getId());
        studygroup.setLeaderMember(member);

//        Member member = memberService.findVerifiedMember(1L);
//        studygroup.setLeaderMember(member);

        return repository.save(studygroup);
    }

    /**
     * 스터디 정보 수정
     * @param studygroup
     * @return
     */
    public Studygroup updateStudygroup(Studygroup studygroup) {

        Studygroup findStudygroup = findStudygroup(studygroup.getId());

        // FIXME: 2023-05-11 스터디 리더인지 확인하기 위한 멤버 불러오기 실패, 추후 확인 필요
//        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        member = memberService.findVerifiedMember(member.getId());
//
//        if (findStudygroup.getLeaderMember().getEmail().equals(member.getEmail())) {
            Optional.ofNullable(studygroup.getStudyName()).ifPresent(findStudygroup::setStudyName);
            Optional.ofNullable(studygroup.getDaysOfWeek()).ifPresent(findStudygroup::setDaysOfWeek);
            Optional.ofNullable(studygroup.getStudyPeriodStart()).ifPresent(findStudygroup::setStudyPeriodStart);
            Optional.ofNullable(studygroup.getStudyPeriodEnd()).ifPresent(findStudygroup::setStudyPeriodEnd);
            Optional.ofNullable(studygroup.getStudyTimeStart()).ifPresent(findStudygroup::setStudyTimeStart);
            Optional.ofNullable(studygroup.getStudyTimeEnd()).ifPresent(findStudygroup::setStudyTimeEnd);
            Optional.ofNullable(studygroup.getIntroduction()).ifPresent(findStudygroup::setIntroduction);
            Optional.ofNullable(studygroup.getMemberCountMin()).ifPresent(findStudygroup::setMemberCountMin);
            Optional.ofNullable(studygroup.getMemberCountMax()).ifPresent(findStudygroup::setMemberCountMax);
            Optional.ofNullable(studygroup.getPlatform()).ifPresent(findStudygroup::setPlatform);
            Optional.ofNullable(studygroup.getSearchTags()).ifPresent(findStudygroup::setSearchTags);
//        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);

        return repository.save(findStudygroup);
    }

    /**
     * 스터디 모집 상태 수정
     * @param studygroupId
     */
    public void updateStatusStudygroup(Long studygroupId) {
        Studygroup findStudygroup = findStudygroup(studygroupId);

        // FIXME: 2023-05-11 스터디 리더인지 확인하기 위한 멤버 불러오기 실패, 추후 확인 필요
//        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        member = memberService.findVerifiedMember(member.getId());
//
//        if (findStudygroup.getLeaderMember().getEmail().equals(member.getEmail())) {
            boolean requited = findStudygroup.getIs_requited();
            if (requited) requited = false;
            else requited = true;
            findStudygroup.setIs_requited(requited);
//        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);

        repository.save(findStudygroup);
    }

    /**
     * 스터디 조회
     * @param studygroupId
     * @return
     */
    public Studygroup findStudygroup(Long studygroupId) {

        Studygroup findStudygroup = repository.findById(studygroupId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STUDYGROUP_NOT_FOUND));

        return findStudygroup;
    }

    public Page<Studygroup> findStudygroups(int page, int size) {
        return repository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    /**
     * 스터디 삭제
     * @param studygroupId
     * @throws Exception
     */
    public void deleteStudygroup(Long studygroupId) throws Exception{

        Studygroup findStudygroup = findStudygroup(studygroupId);

        // FIXME: 2023-05-11 스터디 리더인지 확인하기 위한 멤버 불러오기 실패, 추후 확인 필요
//        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        member = memberService.findVerifiedMember(member.getId());
//
//        if (findStudygroup.getLeaderMember().getEmail().equals(member.getEmail())) {
//            repository.deleteById(studygroupId);
//        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);

        repository.deleteById(studygroupId);
    }
}
