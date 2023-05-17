package com.codestates.edusync.model.study.plancalendar.service;

import com.codestates.edusync.model.common.utils.VerifyStudygroupCalendarUtils;
import com.codestates.edusync.model.common.utils.VerifyStudygroupUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.repository.CalendarStudygroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CalendarStudygroupService implements CalendarStudygroupManager {
    private final CalendarStudygroupRepository calendarStudygroupRepository;
    private final VerifyStudygroupCalendarUtils verifyStudygroupCalendarUtils;
    private final VerifyStudygroupUtils verifyStudygroupUtils;

    @Override
    public void createTimeSchedules(Long studygroupId,
                                    List<TimeSchedule> timeSchedules,
                                    Member loginMember) {
        Studygroup findStudygroup = verifyStudygroupUtils.findStudygroup(studygroupId);

        timeSchedules.forEach(ts -> {
            ts.setStudygroup(findStudygroup);
            ts.setTitle(findStudygroup.getStudyName());
            ts.setContent((findStudygroup.getPlatform()));
        } );
        calendarStudygroupRepository.saveAll(timeSchedules);
    }

    @Override
    public void createTimeSchedulesOfAllMember(Long studygroupId,
                                               List<TimeSchedule> timeSchedules,
                                               Member loginMember) {
        // TODO: 2023-05-11 나중에 구현할거임 ! ADV
    }

    @Override
    public void updateTimeSchedule(Long studygroupId, Long timeScheduleId,
                                   TimeSchedule timeSchedule,
                                   Member loginMember) {
        TimeSchedule findTimeSchedule = verifyStudygroupCalendarUtils.findVerifyTimeSchedule(timeScheduleId);

        Optional.ofNullable(timeSchedule.getTitle()).ifPresent(findTimeSchedule::setTitle);
        Optional.ofNullable(timeSchedule.getContent()).ifPresent(findTimeSchedule::setContent);
        Optional.ofNullable(timeSchedule.getStartTime()).ifPresent(findTimeSchedule::setStartTime);
        Optional.ofNullable(timeSchedule.getEndTime()).ifPresent(findTimeSchedule::setEndTime);
        
        calendarStudygroupRepository.save(findTimeSchedule);
    }

    @Override
    public List<TimeSchedule> getTimeSchedules(Long studygroupId) {
        return calendarStudygroupRepository.findAllByStudygroupId(studygroupId);
    }

    @Override
    public TimeSchedule getSingleTimeScheduleByTimeScheduleId(Long studygroupId, Long timeScheduleId) {

        return verifyStudygroupCalendarUtils.findVerifyTimeSchedule(timeScheduleId);
    }

    @Override
    public void deleteAllTimeSchedules(Long studygroupId, Member loginMember) {
        List<TimeSchedule> findTimeSchedules = calendarStudygroupRepository.findAllByStudygroupId(studygroupId);
        
        calendarStudygroupRepository.deleteAll(findTimeSchedules);
    }

    @Override
    public void deleteTimeScheduleByTimeScheduleId(Long studygroupId, Long timeScheduleId, Member loginMember) {
        TimeSchedule findTimeSchedule = verifyStudygroupCalendarUtils.findVerifyTimeSchedule(timeScheduleId);
        
        calendarStudygroupRepository.delete(findTimeSchedule);
    }

    @Override
    public void deleteTimeScheduleWithSameTimeOfMember(Long studygroupId, Long timeScheduleId, Member loginMember) {
        // TODO: 2023-05-11 ADV 에서 구현할거임 !!! 
    }
}
