package com.codestates.edusync.model.study.plancalendar.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.common.utils.VerifyCalendarUtils;
import com.codestates.edusync.model.common.utils.VerifyStudygroupUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.codestates.edusync.exception.ExceptionCode.TIME_SCHEDULE_NOT_LINKED;
import static com.codestates.edusync.exception.ExceptionCode.TIME_SCHEDULE_NOT_MATCHED_WITH_MEMBER;

@Transactional
@RequiredArgsConstructor
@Service
public class CalendarMemberService {
    private final CalendarRepository calendarRepository;
    private final VerifyCalendarUtils verifyCalendarUtils;
    private final VerifyStudygroupUtils verifyStudygroupUtils;
    private final MemberUtils memberUtils;

public void createTimeSchedulesExceptStudygroup(TimeSchedule timeSchedule,
                                                String email) {
        Member loginMember = memberUtils.getLoggedIn(email);

        timeSchedule.setMember(loginMember);

        calendarRepository.save(timeSchedule);
    }

    public void updateTimeSchedule(Long timeScheduleId,
                                   TimeSchedule timeSchedule,
                                   String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        TimeSchedule findTimeSchedule = verifyCalendarUtils.findVerifyTimeSchedule(timeScheduleId);

        // todo: 예외 리펙터링 필요
        if( findTimeSchedule.getMember() == null ) {
            throw new BusinessLogicException(TIME_SCHEDULE_NOT_LINKED);
        }

        if( !findTimeSchedule.getMember().getId().equals(loginMember.getId()) ) {
            throw new BusinessLogicException(TIME_SCHEDULE_NOT_MATCHED_WITH_MEMBER);
        }

        if( findTimeSchedule.getStudygroup() != null ) {
            throw new BusinessLogicException(TIME_SCHEDULE_NOT_MATCHED_WITH_MEMBER);
        }

        Optional.ofNullable(timeSchedule.getTitle()).ifPresent(findTimeSchedule::setTitle);
        Optional.ofNullable(timeSchedule.getPlatform()).ifPresent(findTimeSchedule::setPlatform);

        findTimeSchedule.setTime(
                new TimeRange(
                        (timeSchedule.getTime().getStudyTimeStart() == null ?
                                findTimeSchedule.getTime().getStudyTimeStart()
                                : timeSchedule.getTime().getStudyTimeStart() ),
                        (timeSchedule.getTime().getStudyTimeEnd() == null ?
                                findTimeSchedule.getTime().getStudyTimeEnd()
                                : timeSchedule.getTime().getStudyTimeEnd() ) )
        );

        calendarRepository.save(findTimeSchedule);
    }

    public List<TimeSchedule> getTimeSchedules(String email) {
        return calendarRepository.findAllByMemberEmail(email);
    }

    public TimeSchedule getSingleTimeScheduleByTimeScheduleId(Long timeScheduleId) {

        return verifyCalendarUtils.findVerifyTimeSchedule(timeScheduleId);
    }

    public void deleteTimeScheduleByTimeScheduleId(Long timeScheduleId,
                                                   String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        TimeSchedule findTimeSchedule = verifyCalendarUtils.findVerifyTimeSchedule(timeScheduleId);

        calendarRepository.delete(findTimeSchedule);
    }
}
