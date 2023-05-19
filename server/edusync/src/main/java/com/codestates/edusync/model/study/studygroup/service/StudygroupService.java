package com.codestates.edusync.model.study.studygroup.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.model.common.entity.DateRange;
import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.common.utils.VerifyStudygroupUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.studygroup.repository.StudygroupRepository;
import com.codestates.edusync.model.study.studygroupjoin.entity.StudygroupJoin;
import com.codestates.edusync.model.study.studygroupjoin.service.StudygroupJoinService;
import com.codestates.edusync.model.studyaddons.searchtag.service.SearchTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class StudygroupService implements StudygroupManager{
    private final StudygroupRepository studygroupRepository;
    private final StudygroupJoinService studygroupJoinService;
    private final SearchTagService searchTagService;
    private final VerifyStudygroupUtils studygroupUtils;
    private final MemberUtils memberUtils;

    @Override
    public Studygroup create(Studygroup studygroup, String email) {
        studygroup.setLeaderMember(memberUtils.getLoggedIn(email));
        return studygroupRepository.save(studygroup);
    }

    @Override
    public Studygroup update(String email, Studygroup studygroup) {
        studygroupUtils.studygroupLeaderCheck(email, studygroup.getId());
        Studygroup findStudygroup = get(studygroup.getId());

        Optional.ofNullable(studygroup.getStudyName()).ifPresent(findStudygroup::setStudyName);
        Optional.ofNullable(studygroup.getDaysOfWeek()).ifPresent(findStudygroup::setDaysOfWeek);

        findStudygroup.setDate(
                new DateRange(
                        (studygroup.getDate().getStudyPeriodStart() == null ?
                                findStudygroup.getDate().getStudyPeriodStart()
                                : studygroup.getDate().getStudyPeriodStart() ),
                        (studygroup.getDate().getStudyPeriodEnd() == null ?
                                findStudygroup.getDate().getStudyPeriodEnd()
                                : studygroup.getDate().getStudyPeriodEnd() )
                )
        );
        findStudygroup.setTime(
                new TimeRange(
                        (studygroup.getTime().getStudyTimeStart() == null ?
                                findStudygroup.getTime().getStudyTimeStart()
                                : studygroup.getTime().getStudyTimeStart() ),
                        (studygroup.getTime().getStudyTimeEnd() == null ?
                                findStudygroup.getTime().getStudyTimeEnd()
                                : studygroup.getTime().getStudyTimeEnd() )
                )
        );
        Optional.ofNullable(studygroup.getIntroduction()).ifPresent(findStudygroup::setIntroduction);
        Optional.ofNullable(studygroup.getMemberCountMin()).ifPresent(findStudygroup::setMemberCountMin);
        Optional.ofNullable(studygroup.getMemberCountMax()).ifPresent(findStudygroup::setMemberCountMax);
        Optional.ofNullable(studygroup.getMemberCountCurrent()).ifPresent(findStudygroup::setMemberCountCurrent);
        Optional.ofNullable(studygroup.getPlatform()).ifPresent(findStudygroup::setPlatform);
        Optional.ofNullable(studygroup.getSearchTags()).ifPresent(findStudygroup::setSearchTags);

        return studygroupRepository.save(findStudygroup);
    }

    @Override
    public boolean updateStatus(String email, Long studygroupId) {
        studygroupUtils.studygroupLeaderCheck(email, studygroupId);
        Studygroup findStudygroup = get(studygroupId);
        findStudygroup.setIsRecruited(!findStudygroup.getIsRecruited());
        studygroupRepository.save(findStudygroup);
        return findStudygroup.getIsRecruited();
    }

    @Override
    public Studygroup get(Long studygroupId) {
        Studygroup findStudygroup = studygroupUtils.findVerifyStudygroup(studygroupId);

        // todo : searchTag null 값으로 수동 셋, 확인 필요
        findStudygroup.setSearchTags(searchTagService.getList(studygroupId));

        // todo : 스터디 멤버 카운트 확인 필요
        //findStudygroup.setMemberCountCurrent(studygroupJoinService.getStudygroupMemberCount(studygroupId));
        findStudygroup.setMemberCountCurrent(studygroupJoinService.getAllMemberList(studygroupId).size()+1);

        return findStudygroup;
    }

    @Override
    public Page<Studygroup> getWithPaging(Integer page, Integer size) {
        return studygroupRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @Override
    public List<Studygroup> getLeaderStudygroupList(String email) {
        Member member = memberUtils.get(email);
        return studygroupRepository.findAllByLeaderMemberId(member.getId());
    }

    @Override
    public void delete(String email, Long studygroupId) {
        if (studygroupUtils.isMemberLeaderOfStudygroup(email, studygroupId)) {
            studygroupRepository.deleteById(studygroupId);
        } else throw new BusinessLogicException(ExceptionCode.INVALID_PERMISSION);
    }

    @Override
    public void patchLeader(String email, Long studygroupId, String newLeaderNickName) {
        studygroupUtils.studygroupLeaderCheck(email, studygroupId);
        Studygroup findStudygroup = get(studygroupId);
        List<StudygroupJoin> studygroupJoins = findStudygroup.getStudygroupJoins();
        Member member = null;

        for (StudygroupJoin studygroupJoin : studygroupJoins) {
            if (studygroupJoin.getMember().getNickName().equals(newLeaderNickName)) {
                member = studygroupJoin.getMember();
                break;
            }
        }

        if (member == null) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        findStudygroup.setLeaderMember(member);
        studygroupRepository.save(findStudygroup);

        Member oldLeader = memberUtils.get(email);
        Member newLeader = member;
        studygroupJoinService.leaderChanged(newLeader, oldLeader);
    }
}
