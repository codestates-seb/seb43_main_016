package com.codestates.edusync.infodto.calendarinfo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
public class CalendarInfoDto {
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private Timestamp start;

    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private Timestamp end;
}
