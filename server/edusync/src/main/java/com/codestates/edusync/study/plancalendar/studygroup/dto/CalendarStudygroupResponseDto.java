package com.codestates.edusync.study.plancalendar.studygroup.dto;

import com.codestates.edusync.infodto.timeschedule.dto.TimeScheduleResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CalendarStudygroupResponseDto {
    private Long id;

    private List<TimeScheduleResponseDto> timeSchedules;
}
