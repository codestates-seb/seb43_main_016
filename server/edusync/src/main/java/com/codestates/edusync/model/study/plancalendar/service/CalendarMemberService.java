package com.codestates.edusync.model.study.plancalendar.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.model.common.utils.VerifyCalendarUtils;
import com.codestates.edusync.model.common.utils.VerifyTimeScheduleUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CalendarMemberService {
    private final CalendarRepository calendarRepository;
    private final VerifyCalendarUtils calendarUtils;
    private final VerifyTimeScheduleUtils timeScheduleUtils;

    public void createTimeSchedulesExceptStudygroup(TimeSchedule timeSchedule,
                                                    Member loginMember) {
        if( timeSchedule.getTime().getStudyTimeStart() == null ||
            timeSchedule.getTime().getStudyTimeEnd() == null ) {
            throw new BusinessLogicException(ExceptionCode.TIME_SCHEDULE_NOT_NULL_ALLOWED);
        }

        timeSchedule.setMember(loginMember);

        calendarRepository.save(timeSchedule);
    }

    public void updateTimeSchedule(Long timeScheduleId,
                                   TimeSchedule timeSchedule,
                                   Member loginMember) {
        TimeSchedule findTimeSchedule =
                timeScheduleUtils.findVerifyTimeScheduleWithMember(
                        timeScheduleId, loginMember
                );

        CommonCalendarFeature.setTimeScheduleInformation(timeSchedule, findTimeSchedule);

        calendarRepository.save(findTimeSchedule);
    }

    public List<TimeSchedule> getTimeSchedules(String email) {

        return calendarRepository.findAllByMemberEmail(email);
    }

    public TimeSchedule getSingleTimeScheduleByTimeScheduleId(Long timeScheduleId) {

        return calendarUtils.findVerifyTimeSchedule(timeScheduleId);
    }

    public void deleteTimeScheduleByTimeScheduleId(Long timeScheduleId,
                                                   Member loginMember) {
        TimeSchedule findTimeSchedule =
                timeScheduleUtils.findVerifyTimeScheduleWithMember(
                        timeScheduleId, loginMember
                );

        calendarRepository.delete(findTimeSchedule);
    }
}
