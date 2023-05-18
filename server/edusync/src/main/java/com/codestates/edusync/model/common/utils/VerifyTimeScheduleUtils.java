package com.codestates.edusync.model.common.utils;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VerifyTimeScheduleUtils {
    private final CalendarRepository calendarRepository;

    public TimeSchedule findVerifyTimeSchedule(Long timeScheduleId) {
        return calendarRepository.findById(timeScheduleId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TIME_SCHEDULE_NOT_FOUND));
    }
}
