package com.codestates.edusync.study.plancalendar.timeschedule.dto;

import com.codestates.edusync.study.calendarinfo.dto.CalendarInfoSingleResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class TimeScheduleResponseDto {
    private Long id;

    private String studygroupName;
    private String platform;

    private TimeScheduleSingleResponseDto timeScheduleInfo;
    private CalendarInfoSingleResponseDto calendarInfo;
}
