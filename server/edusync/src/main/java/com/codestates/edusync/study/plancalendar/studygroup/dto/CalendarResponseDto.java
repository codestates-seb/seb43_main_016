package com.codestates.edusync.study.plancalendar.studygroup.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class CalendarResponseDto {
    private Long id;
    private Long groupId;

    private String title;
    private Boolean allDay;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp start;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp end;

    private String description;
    private Boolean overlap;

    private Map<String, String> extendedProps = new HashMap<>();

    private String color;
}
