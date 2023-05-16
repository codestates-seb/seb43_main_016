package com.codestates.edusync.study.plancalendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private TimeScheduleDto timeScheduleInfo;
    private CalendarInfoDto calendarInfo;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class CalendarInfoDto {
        @JsonFormat(pattern = "MM-dd", timezone = "Asia/Seoul")
        private Timestamp start;

        @JsonFormat(pattern = "MM-dd", timezone = "Asia/Seoul")
        private Timestamp end;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class TimeScheduleDto {
        @JsonFormat(pattern = "MM-dd HH:mm", timezone = "Asia/Seoul")
        private Timestamp start;

        @JsonFormat(pattern = "MM-dd HH:mm", timezone = "Asia/Seoul")
        private Timestamp end;
    }
}
