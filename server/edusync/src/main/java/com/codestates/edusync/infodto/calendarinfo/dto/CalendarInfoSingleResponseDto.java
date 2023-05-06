package com.codestates.edusync.infodto.calendarinfo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class CalendarInfoSingleResponseDto {
    private Long id;

    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private Timestamp startCalendar;

    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private Timestamp endCalendar;
}
