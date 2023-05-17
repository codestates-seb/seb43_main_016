package com.codestates.edusync.model.study.plancalendar.mapper;

import com.codestates.edusync.model.common.dto.DateRangeDto;
import com.codestates.edusync.model.common.dto.TimeRangeDto;
import com.codestates.edusync.model.study.plancalendar.dto.TimeScheduleResponseDto;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.dto.CalendarDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarStudygroupMapper {

    @Named("PostDtoToEntity")
    TimeSchedule timeSchedulePostDtoToTimeSchedule(CalendarDto.TimeScheduleDto.Post timeSchedule);

    @IterableMapping(qualifiedByName = "PostDtoToEntity")
    List<TimeSchedule> timeSchedulePostDtoListToTimeScheduleList(List<CalendarDto.TimeScheduleDto.Post> timeSchedules);
    TimeSchedule timeSchedulePatchDtoToTimeSchedule(CalendarDto.Patch patchDto);

    List<TimeRangeDto.Response> timeScheduleListToTimeScheduleResponseDto(List<TimeSchedule> timeSchedules);

    default TimeScheduleResponseDto timeScheduleToTimeScheduleResponseDto(TimeSchedule ts) {
        TimeScheduleResponseDto result = new TimeScheduleResponseDto();
        result.setId(ts.getId());
        result.setStudyName(ts.getStudygroup().getStudyName());
        result.setPlatform(ts.getStudygroup().getPlatform());

        TimeRangeDto.Response resultTR = new TimeRangeDto.Response();
        resultTR.setStudyTimeStart(ts.getTime().getStudyTimeStart());
        resultTR.setStudyTimeEnd(ts.getTime().getStudyTimeEnd());
        result.setTimeScheduleInfo(resultTR);

        DateRangeDto.Response resultDR = new DateRangeDto.Response();
        resultDR.setStudyPeriodStart(ts.getStudygroup().getDate().getStudyPeriodStart());
        resultDR.setStudyPeriodEnd(ts.getStudygroup().getDate().getStudyPeriodEnd());
        result.setCalendarInfo(resultDR);

        return result;
    }
}
