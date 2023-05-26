package com.codestates.edusync.model.study.plancalendar.dto;

import com.codestates.edusync.model.common.dto.DateRangeDto;
import com.codestates.edusync.model.common.dto.TimeRangeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class CalendarMemberResponseDto {
    private Long calendarId;

    private String title;
    private Boolean allDay;

    private TimeRangeDto.Response schedule;

    private String platform;
    private String description;
    private Boolean overlap = true;     // 일정이 겹쳤을 때 허용할지 말지 ( true = 허용 )

    private String color;
}
