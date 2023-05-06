package com.codestates.edusync.infodto.timeschedule.dto;

import com.codestates.edusync.infodto.calendarinfo.dto.CalendarInfoSingleResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
