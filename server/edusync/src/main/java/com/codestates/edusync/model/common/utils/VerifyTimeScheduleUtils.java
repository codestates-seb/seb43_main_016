package com.codestates.edusync.model.common.utils;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.codestates.edusync.exception.ExceptionCode.TIME_SCHEDULE_NOT_LINKED_WITH_MEMBER;
import static com.codestates.edusync.exception.ExceptionCode.TIME_SCHEDULE_NOT_MATCHED_WITH_MEMBER;

@RequiredArgsConstructor
@Component
public class VerifyTimeScheduleUtils {
    private final MemberUtils memberUtils;
    private final VerifyCalendarUtils verifyCalendarUtils;
    private final CalendarRepository calendarRepository;

    public TimeSchedule findVerifyTimeSchedule(Long timeScheduleId) {
        return calendarRepository.findById(timeScheduleId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TIME_SCHEDULE_NOT_FOUND));
    }

    public TimeSchedule findVerifyTimeScheduleWithMember(Long timeScheduleId, String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        TimeSchedule findTimeSchedule = verifyCalendarUtils.findVerifyTimeSchedule(timeScheduleId);

        if( findTimeSchedule.getMember() == null ) {
            throw new BusinessLogicException(TIME_SCHEDULE_NOT_LINKED_WITH_MEMBER);
        }

        if( !findTimeSchedule.getMember().getId().equals(loginMember.getId()) ) {
            throw new BusinessLogicException(TIME_SCHEDULE_NOT_MATCHED_WITH_MEMBER);
        }

        if( findTimeSchedule.getStudygroup() != null ) {
            throw new BusinessLogicException(TIME_SCHEDULE_NOT_MATCHED_WITH_MEMBER);
        }
        return findTimeSchedule;
    }
}
