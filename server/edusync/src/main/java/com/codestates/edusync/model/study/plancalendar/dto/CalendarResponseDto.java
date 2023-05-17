package com.codestates.edusync.model.study.plancalendar.dto;

import com.codestates.edusync.model.common.dto.TimeRangeDto;
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
public class CalendarResponseDto extends TimeRangeDto.Response {
    private Long id;
    private Long groupId;

    private String title;
    private String content;
    private Boolean allDay;

    private String description;
    private Boolean overlap;

    private Map<String, String> extendedProps = new HashMap<>();

    private String color;
}
