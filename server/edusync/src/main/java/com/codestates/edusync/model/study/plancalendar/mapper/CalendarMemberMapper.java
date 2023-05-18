package com.codestates.edusync.model.study.plancalendar.mapper;

import com.codestates.edusync.model.common.dto.TimeRangeDto;
import com.codestates.edusync.model.study.plancalendar.dto.TimeScheduleResponseDto;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.dto.CalendarDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;

import static com.codestates.edusync.model.study.plancalendar.mapper.CalendarStudygroupMapper.setDateAndTimeIntoTimeScheduleResponseDto;

@Mapper(componentModel = "spring")
public interface CalendarMemberMapper {

    @Named("PostDtoToEntity")
    TimeSchedule timeSchedulePostDtoToTimeSchedule(CalendarDto.TimeScheduleDto.Post timeSchedule);

    @IterableMapping(qualifiedByName = "PostDtoToEntity")
    List<TimeSchedule> timeSchedulePostDtoListToTimeScheduleList(List<CalendarDto.TimeScheduleDto.Post> timeSchedules);
    TimeSchedule timeSchedulePatchDtoToTimeSchedule(CalendarDto.Patch patchDto);

    List<TimeRangeDto.Response> timeScheduleListToTimeScheduleResponseDto(List<TimeSchedule> timeSchedules);

    default TimeScheduleResponseDto timeScheduleToTimeScheduleResponseDto(TimeSchedule ts) {
        TimeScheduleResponseDto result = new TimeScheduleResponseDto();
        result.setId(ts.getId());

        Optional.ofNullable(ts.getStudygroup().getStudyName()).ifPresent(result::setStudyName);
        Optional.ofNullable(ts.getStudygroup().getPlatform()).ifPresent(result::setPlatform);

        setDateAndTimeIntoTimeScheduleResponseDto(ts, result);

        return result;
    }
}
