package com.codestates.edusync.study.calendarinfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class CalendarInfoSingleResponseDto {
    private Long id;

    private Timestamp startCalendar;
    private Timestamp endCalendar;
}
