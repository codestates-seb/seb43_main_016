package com.codestates.edusync.study.plancalendar.studygroup.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.infodto.timeschedule.entity.TimeSchedule;
import com.codestates.edusync.study.plancalendar.studygroup.repository.CalendarStudygroupRepository;
import com.codestates.edusync.study.plancalendar.studygroup.utils.CalendarStudygroupManager;
import com.codestates.edusync.study.postcomment.entity.StudygroupPostComment;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.codestates.edusync.exception.ExceptionCode.STUDYGROUP_POST_COMMENT_NOT_FOUND;
import static com.codestates.edusync.exception.ExceptionCode.TIME_SCHEDULE_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CalendarStudygroupService implements CalendarStudygroupManager {
    private final CalendarStudygroupRepository calendarStudygroupRepository;
    @Override
    public void createTimeSchedulesForStudygroup(Long studyGroupId,
                                                 List<TimeSchedule> timeSchedules) {
        // FIXME: 2023-05-11: 스터디 그룹 머지하면 적용. 지금은 동작 하지 않는다!
//        Studygroup findStudygroup = studygroupService.findVerifyStudygroup(studygroupId);
//
//        timeSchedules.forEach(ts -> {
//            ts.setStudygroup(findStudygroup);
//            ts.setTitle(findStudygroup.getStudyName());
//            ts.setContent((findStudygroup.getPlatform()));
//        } );
        calendarStudygroupRepository.saveAll(timeSchedules);
    }

    @Override
    public void createTimeSchedulesOfAllMember(Long studygroupId,
                                               List<TimeSchedule> timeSchedules) {
        // TODO: 2023-05-11 나중에 구현할거임 ! ADV
    }

    @Override
    public void updateStudygroupTimeSchedule(Long studygroupId, Long timeScheduleId,
                                             TimeSchedule timeSchedule) {
        TimeSchedule findTimeSchedule = findVerifyTimeSchedule(timeScheduleId);

        Optional.ofNullable(timeSchedule.getTitle()).ifPresent(findTimeSchedule::setTitle);
        Optional.ofNullable(timeSchedule.getContent()).ifPresent(findTimeSchedule::setContent);
        Optional.ofNullable(timeSchedule.getStart()).ifPresent(findTimeSchedule::setStart);
        Optional.ofNullable(timeSchedule.getEnd()).ifPresent(findTimeSchedule::setEnd);
        
        calendarStudygroupRepository.save(findTimeSchedule);
    }

    @Override
    public List<TimeSchedule> getTimeSchedulesByStudygroupId(Long studygroupId) {
        return calendarStudygroupRepository.findAllByStudygroupId(studygroupId);
    }

    @Override
    public TimeSchedule getSingleTimeScheduleById(Long studygroupId, Long timeScheduleId) {
        return findVerifyTimeSchedule(timeScheduleId);
    }

    @Override
    public void deleteAllTimeSchedulesByStudygroupId(Long studygroupId) {
        List<TimeSchedule> findTimeSchedules = calendarStudygroupRepository.findAllByStudygroupId(studygroupId);
        
        calendarStudygroupRepository.deleteAll(findTimeSchedules);
    }

    @Override
    public void deleteTimeScheduleByTimeScheduleId(Long studygroupId, Long timeScheduleId) {
        TimeSchedule findTimeSchedule = findVerifyTimeSchedule(timeScheduleId);
        
        calendarStudygroupRepository.delete(findTimeSchedule);
    }

    @Override
    public void deleteTimeScheduleWithSameTimeOfMember(Long studygroupId, Long timeScheduleId) {
        // TODO: 2023-05-11 ADV 에서 구현할거임 !!! 
    }

    /**
     * <h2>일정 조회</h2>
     * 일정 식별자에 해당하는 일정이 존재하는지 확인<br>
     * <font color=white>404 Not Found </font> 일정이 존재하지 않음 !<br>
     * @param timeScheduleId
     * @return
     */
    private TimeSchedule findVerifyTimeSchedule(Long timeScheduleId) {
        Optional<TimeSchedule> optionalTimeSchedule = calendarStudygroupRepository.findById(timeScheduleId);

        TimeSchedule findTimeSchedule =
                optionalTimeSchedule.orElseThrow( () ->
                        new BusinessLogicException(TIME_SCHEDULE_NOT_FOUND)
                );

        return findTimeSchedule;
    }
}
