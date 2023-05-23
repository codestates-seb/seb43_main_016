package com.codestates.edusync.model.study.plancalendar.service;

import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.common.utils.VerifyCalendarUtils;
import com.codestates.edusync.model.common.utils.VerifyStudygroupUtils;
import com.codestates.edusync.model.common.utils.VerifyTimeScheduleUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CalendarMemberService {
    private final CalendarRepository calendarRepository;
    private final VerifyCalendarUtils calendarUtils;
    private final VerifyTimeScheduleUtils timeScheduleUtils;
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
        TimeSchedule findTimeSchedule =
                timeScheduleUtils.findVerifyTimeScheduleWithMember(
                        timeScheduleId, email
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
                                                   String email) {
        TimeSchedule findTimeSchedule =
                timeScheduleUtils.findVerifyTimeScheduleWithMember(
                        timeScheduleId, email
                );

        calendarRepository.delete(findTimeSchedule);
    }
}
