package com.codestates.edusync.model.study.plancalendar.service;

import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.common.utils.VerifyCalendarUtils;
import com.codestates.edusync.model.common.utils.VerifyStudygroupUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
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
public class CalendarStudygroupService implements CalendarStudygroupManager {
    private final CalendarRepository calendarRepository;
    private final VerifyCalendarUtils verifyCalendarUtils;
    private final VerifyStudygroupUtils verifyStudygroupUtils;
    private final MemberUtils memberUtils;

    @Override
    public void createTimeSchedules(Long studygroupId,
                                    List<TimeSchedule> timeSchedules,
                                    String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        Studygroup findStudygroup = verifyStudygroupUtils.findVerifyStudygroup(studygroupId);

        timeSchedules.forEach(ts -> {
            ts.setStudygroup(findStudygroup);
            ts.setTitle(findStudygroup.getStudyName());
            ts.setContent((findStudygroup.getPlatform()));
        } );
        calendarRepository.saveAll(timeSchedules);
    }

    @Override
    public void createTimeSchedulesOfAllMember(Long studygroupId,
                                               List<TimeSchedule> timeSchedules,
                                               String email) {
        Member loginMember = memberUtils.getLoggedIn(email);

        // TODO: 2023-05-11 나중에 구현할거임 ! ADV
    }

    @Override
    public void updateTimeSchedule(Long studygroupId, Long timeScheduleId,
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

    @Override
    public List<TimeSchedule> getTimeSchedules(Long studygroupId) {
        return calendarRepository.findAllByStudygroupIdAndMemberIsNull(studygroupId);
    }

    @Override
    public TimeSchedule getSingleTimeScheduleByTimeScheduleId(Long studygroupId, Long timeScheduleId) {

        return verifyCalendarUtils.findVerifyTimeSchedule(timeScheduleId);
    }

    @Override
    public void deleteAllTimeSchedules(Long studygroupId,
                                       String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        List<TimeSchedule> findTimeSchedules = calendarRepository.findAllByStudygroupId(studygroupId);
        
        calendarRepository.deleteAll(findTimeSchedules);
    }

    @Override
    public void deleteTimeScheduleByTimeScheduleId(Long studygroupId,
                                                   Long timeScheduleId,
                                                   String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        TimeSchedule findTimeSchedule = verifyCalendarUtils.findVerifyTimeSchedule(timeScheduleId);
        
        calendarRepository.delete(findTimeSchedule);
    }

    @Override
    public void deleteTimeScheduleWithSameTimeOfMember(Long studygroupId,
                                                       Long timeScheduleId,
                                                       String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        // TODO: 2023-05-11 ADV 에서 구현할거임 !!! 
    }
}
