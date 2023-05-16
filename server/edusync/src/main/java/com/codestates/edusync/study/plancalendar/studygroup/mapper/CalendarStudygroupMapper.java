package com.codestates.edusync.study.plancalendar.studygroup.mapper;

import com.codestates.edusync.study.plancalendar.dto.TimeScheduleResponseDto;
import com.codestates.edusync.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.study.plancalendar.studygroup.dto.CalendarDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarStudygroupMapper {

    List<TimeSchedule> timeSchedulePostDtoListToTimeScheduleList(List<CalendarDto.TimeScheduleDto.Post> timeSchedules);
    TimeSchedule timeSchedulePatchDtoToTimeSchedule(CalendarDto.Patch patchDto);

    List<TimeScheduleResponseDto.TimeScheduleDto> timeScheduleListToTimeScheduleResponseDto(List<TimeSchedule> timeSchedules);

    default TimeScheduleResponseDto timeScheduleToTimeScheduleResponseDto(TimeSchedule ts) {
        TimeScheduleResponseDto result = new TimeScheduleResponseDto();
        result.setId(ts.getId());
        result.setStudygroupName(ts.getStudygroup().getStudyName());
        result.setPlatform(ts.getStudygroup().getPlatform());

        TimeScheduleResponseDto.TimeScheduleDto resultTimeScheduleInfo = new TimeScheduleResponseDto.TimeScheduleDto();
        resultTimeScheduleInfo.setStart(ts.getStart());
        resultTimeScheduleInfo.setEnd(ts.getEnd());
        result.setTimeScheduleInfo(resultTimeScheduleInfo);

        TimeScheduleResponseDto.CalendarInfoDto resultCalendarInfo = new TimeScheduleResponseDto.CalendarInfoDto();
        resultCalendarInfo.setStart(ts.getStudygroup().getStudyPeriodStart());
        resultCalendarInfo.setEnd(ts.getStudygroup().getStudyPeriodEnd());
        result.setCalendarInfo(resultCalendarInfo);

        return result;
    }
}
