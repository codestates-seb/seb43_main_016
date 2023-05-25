package com.codestates.edusync.model.study.plancalendar.dto;

import com.codestates.edusync.model.common.dto.DateRangeDto;
import com.codestates.edusync.model.common.dto.TimeRangeDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TimeScheduleResponseDto {
    private Long id;

    private String studyName;
    private String platform;

    private TimeRangeDto.Response timeScheduleInfo;
    private DateRangeDto.OnlyPeriodResponse calendarInfo;
}
