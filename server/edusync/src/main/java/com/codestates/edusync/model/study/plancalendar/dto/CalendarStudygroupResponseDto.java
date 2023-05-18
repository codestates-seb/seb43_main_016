package com.codestates.edusync.model.study.plancalendar.dto;

import com.codestates.edusync.model.common.dto.TimeRangeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class CalendarStudygroupResponseDto extends TimeRangeDto.Response {
    private Long id;
    private Long groupId;

    private String title;
    private String platform;
    private Boolean allDay;

    private String description;
    private Boolean overlap;

    private Map<String, String> extendedProps = new HashMap<>();

    private String color;
}
