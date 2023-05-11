package com.codestates.edusync.study.plancalendar.studygroup.mapper;

import com.codestates.edusync.infodto.timeschedule.dto.TimeScheduleResponseDto;
import com.codestates.edusync.infodto.timeschedule.entity.TimeSchedule;
import com.codestates.edusync.study.plancalendar.studygroup.dto.CalendarDto;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarStudygroupMapper {

    @Named("PostDtoToEntity")
    TimeSchedule timeSchedulePostDtoToTimeSchedule(CalendarDto.TimeScheduleDto.Post timeSchedule);

    @IterableMapping(qualifiedByName = "PostDtoToEntity")
    List<TimeSchedule> timeSchedulePostDtoListToTimeScheduleList(List<CalendarDto.TimeScheduleDto.Post> timeSchedules);
    TimeSchedule timeSchedulePatchDtoToTimeSchedule(CalendarDto.Patch patchDto);

    List<TimeScheduleResponseDto.TimeScheduleDto> timeScheduleListToTimeScheduleResponseDto(List<TimeSchedule> timeSchedules);

    default TimeScheduleResponseDto timeScheduleToTimeScheduleResponseDto(TimeSchedule ts) {
        TimeScheduleResponseDto result = new TimeScheduleResponseDto();
        result.setId(ts.getId());
        result.setStudyName(ts.getStudygroup().getStudyName());
        result.setPlatform(ts.getStudygroup().getPlatform());

        TimeScheduleResponseDto.TimeScheduleDto resultTimeScheduleInfo = new TimeScheduleResponseDto.TimeScheduleDto();
        resultTimeScheduleInfo.setStartTime(ts.getStartTime());
        resultTimeScheduleInfo.setEndTime(ts.getEndTime());
        result.setTimeScheduleInfo(resultTimeScheduleInfo);

        TimeScheduleResponseDto.CalendarInfoDto resultCalendarInfo = new TimeScheduleResponseDto.CalendarInfoDto();
        resultCalendarInfo.setStartDate(ts.getStudygroup().getStudyPeriodStart());
        resultCalendarInfo.setEndDate(ts.getStudygroup().getStudyPeriodEnd());
        result.setCalendarInfo(resultCalendarInfo);

        return result;
    }
}
