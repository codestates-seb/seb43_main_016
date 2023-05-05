package com.codestates.edusync.study.plancalendar.classmate.dto;

import com.codestates.edusync.study.plancalendar.timeschedule.dto.TimeScheduleResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CalendarClassmateResponseDto {
    private Long id;

    private List<TimeScheduleResponseDto> timeSchedules;
}
