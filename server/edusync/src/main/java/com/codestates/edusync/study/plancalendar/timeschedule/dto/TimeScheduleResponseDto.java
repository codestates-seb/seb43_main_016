package com.codestates.edusync.study.plancalendar.timeschedule.dto;

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

    private Timestamp startTime;
    private Timestamp endTime;

    private Timestamp startCalendar;
    private Timestamp endCalendar;
}
