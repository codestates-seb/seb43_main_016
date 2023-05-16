package com.codestates.edusync.model.common.utils;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.studygroup.repository.CalendarStudygroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.codestates.edusync.exception.ExceptionCode.TIME_SCHEDULE_NOT_FOUND;

@RequiredArgsConstructor
@Component
public class VerifyStudygroupCalendarUtils {

    private final CalendarStudygroupRepository calendarStudygroupRepository;

    /**
     * <h2>일정 조회</h2>
     * 일정 식별자에 해당하는 일정이 존재하는지 확인<br>
     * <font color=white>404 Not Found </font> 일정이 존재하지 않음 !<br>
     * @param timeScheduleId
     * @return
     */
    public TimeSchedule findVerifyTimeSchedule(Long timeScheduleId) {
        return calendarStudygroupRepository.findById(timeScheduleId)
                .orElseThrow( () ->
                        new BusinessLogicException(TIME_SCHEDULE_NOT_FOUND)
                );
    }
}
