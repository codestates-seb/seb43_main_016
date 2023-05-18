package com.codestates.edusync.model.study.plancalendar.service;

import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.common.utils.VerifyCalendarUtils;
import com.codestates.edusync.model.common.utils.VerifyStudygroupUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.repository.CalendarRepository;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
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
    private final VerifyCalendarUtils verifyCalendarUtils;
    private final VerifyStudygroupUtils verifyStudygroupUtils;
    private final MemberUtils memberUtils;

    public void createTimeSchedulesWithStudygroup(Long studygroupId,
                                                  List<TimeSchedule> timeSchedules,
                                                  String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        Studygroup findStudygroup = verifyStudygroupUtils.findVerifyStudygroup(studygroupId);

        timeSchedules.forEach(ts -> {
            ts.setStudygroup(findStudygroup);
            ts.setMember(loginMember);
            ts.setTitle(findStudygroup.getStudyName());
            ts.setContent((findStudygroup.getPlatform()));
        } );
        calendarRepository.saveAll(timeSchedules);
    }

public void createTimeSchedulesExceptStudygroup(TimeSchedule timeSchedule,
                                                String email) {
        Member loginMember = memberUtils.getLoggedIn(email);

        timeSchedule.setMember(loginMember);

        calendarRepository.save(timeSchedule);
    }

    public void createTimeSchedulesOfAllMember(String memberUuid,
                                               List<TimeSchedule> timeSchedules,
                                               String email) {
        Member loginMember = memberUtils.getLoggedIn(email);

        // TODO: 2023-05-11 나중에 구현할거임 ! ADV
    }

    public void updateTimeSchedule(Long timeScheduleId,
                                   TimeSchedule timeSchedule,
                                   String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        TimeSchedule findTimeSchedule = verifyCalendarUtils.findVerifyTimeSchedule(timeScheduleId);

        Optional.ofNullable(timeSchedule.getTitle()).ifPresent(findTimeSchedule::setTitle);
        Optional.ofNullable(timeSchedule.getContent()).ifPresent(findTimeSchedule::setContent);

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

    public TimeSchedule getSingleTimeScheduleByTimeScheduleId(String memberUuid, Long timeScheduleId) {

        return verifyCalendarUtils.findVerifyTimeSchedule(timeScheduleId);
    }

    public void deleteTimeScheduleByTimeScheduleId(Long timeScheduleId,
                                                   String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        TimeSchedule findTimeSchedule = verifyCalendarUtils.findVerifyTimeSchedule(timeScheduleId);

        calendarRepository.delete(findTimeSchedule);
    }

    public void deleteTimeScheduleWithSameTimeOfMember(String memberUuid,
                                                       Long timeScheduleId,
                                                       String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        // TODO: 2023-05-11 ADV 에서 구현할거임 !!! 
    }
}
