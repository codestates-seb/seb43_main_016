package com.codestates.edusync.model.common.utils;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.List;

import static com.codestates.edusync.exception.ExceptionCode.TIME_SCHEDULE_NOT_FOUND;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class VerifyCalendarUtils {

    private final CalendarRepository calendarRepository;

    /**
     * <h2>일정 조회</h2>
     * 일정 식별자에 해당하는 일정이 존재하는지 확인<br>
     * <font color=white>404 Not Found </font> 일정이 존재하지 않음 !<br>
     * @param timeScheduleId
     * @return
     */
    public TimeSchedule findVerifyTimeSchedule(Long timeScheduleId) {
        return calendarRepository.findById(timeScheduleId)
                .orElseThrow( () ->
                        new BusinessLogicException(TIME_SCHEDULE_NOT_FOUND)
                );
    }
}
