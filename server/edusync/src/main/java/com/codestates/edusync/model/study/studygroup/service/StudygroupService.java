package com.codestates.edusync.model.study.studygroup.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.model.common.entity.DateRange;
import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.common.utils.VerifyStudygroupUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.plancalendar.service.CalendarStudygroupService;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.studygroup.repository.StudygroupRepository;
import com.codestates.edusync.model.study.studygroup.utils.ScheduleConverter;
import com.codestates.edusync.model.study.studygroup.utils.StudygroupGetOrder;
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
    private final CalendarStudygroupService calendarStudygroupService;
    private final VerifyStudygroupUtils studygroupUtils;
    private final MemberUtils memberUtils;

    @Override
    public Studygroup create(Studygroup studygroup, String email) {
        studygroup.setLeaderMember(memberUtils.getLoggedIn(email));

        studygroup.setTimeSchedules(
                ScheduleConverter.repeatedScheduleToScheduleListConverter(studygroup)
        );

        Studygroup createdStudygroup = studygroupRepository.save(studygroup);

        studygroupJoinService.createJoinAsLeader(createdStudygroup.getId(), email);

        return createdStudygroup;
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
        Optional.ofNullable(studygroup.getPlatform()).ifPresent(findStudygroup::setPlatform);
        Optional.ofNullable(studygroup.getSearchTags()).ifPresent(findStudygroup::setSearchTags);

        calendarStudygroupService.deleteAllTimeSchedulesByStudygroupId(findStudygroup.getId(), email);
        findStudygroup.setTimeSchedules(
                ScheduleConverter.repeatedScheduleToScheduleListConverter(findStudygroup)
        );

        return studygroupRepository.save(findStudygroup);
    }

    @Override
    public boolean updateStatus(String email, Long studygroupId) {
        studygroupUtils.studygroupLeaderCheck(email, studygroupId);
        Studygroup findStudygroup = get(studygroupId);

        // 모집 완료가 되었더라도 다시 모집할 수 있도록 바꿀 수 있게 함
//        if (findStudygroup.getIsRecruited()) {
//            throw new BusinessLogicException(ExceptionCode.STUDYGROUP_RECRUITED_NOT_MODIFIED);
//        }

        findStudygroup.setIsRecruited(!findStudygroup.getIsRecruited());
        studygroupRepository.save(findStudygroup);
        return findStudygroup.getIsRecruited();
    }

    @Transactional(readOnly = true)
    @Override
    public Studygroup get(Long studygroupId) {
        Studygroup findStudygroup = studygroupUtils.findVerifyStudygroup(studygroupId);

        findStudygroup.setMemberCountCurrent(studygroupJoinService.getStudygroupMemberCount(studygroupId));

        return findStudygroup;
    }

    public Page<Studygroup> getWithPagingAndOrder(Integer page, Integer size, String order, Boolean isAscending) {
        Sort sort = getSortByOrder(order, isAscending);

        return studygroupRepository.findAll(PageRequest.of(page, size, sort));
    }

    private static Sort getSortByOrder(String order, Boolean isAscending) {
        StudygroupGetOrder orderEnum = StudygroupGetOrder.valueOfOrder(order);
        String convertedVariable = orderEnum.getVariable();

        // 오름차순과 내림차순 구분
        Sort sort = Sort.by(convertedVariable);
        if( isAscending )   return sort;
        else                return sort.descending();
    }

    @Override
    public Page<Studygroup> getWithPaging(Integer page, Integer size) {
        return getWithPagingAndOrder(page, size, "기본값", false);
    }


    @Override
    public List<Studygroup> getLeaderStudygroupList(String email) {
        Member member = memberUtils.get(email);
        return studygroupRepository.findAllByLeaderMemberId(member.getId());
    }

    @Override
    public void delete(String email, Long studygroupId) {
        if (studygroupUtils.isMemberLeaderOfStudygroup(email, studygroupId)) {
            calendarStudygroupService.deleteAllTimeSchedulesByStudygroupId(studygroupId, email);
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

        if (member == null) throw new BusinessLogicException(ExceptionCode.STUDYGROUP_PRIVILEGES_MEMBER_NOT_FOUND);
        findStudygroup.setLeaderMember(member);
        studygroupRepository.save(findStudygroup);
    }
}
