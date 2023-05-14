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

    private String studyName;
    private String platform;

    private TimeScheduleDto timeScheduleInfo;
    private CalendarInfoDto calendarInfo;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class CalendarInfoDto {
        @JsonFormat(pattern = "MM-dd", timezone = "Asia/Seoul")
        private Timestamp startDate;

        @JsonFormat(pattern = "MM-dd", timezone = "Asia/Seoul")
        private Timestamp endDate;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class TimeScheduleDto {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private Timestamp startTime;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private Timestamp endTime;
    }
}
