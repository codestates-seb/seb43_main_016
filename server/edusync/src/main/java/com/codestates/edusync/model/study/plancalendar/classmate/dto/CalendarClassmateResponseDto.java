package com.codestates.edusync.model.study.plancalendar.classmate.dto;

import com.codestates.edusync.model.study.plancalendar.dto.TimeScheduleResponseDto;
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
