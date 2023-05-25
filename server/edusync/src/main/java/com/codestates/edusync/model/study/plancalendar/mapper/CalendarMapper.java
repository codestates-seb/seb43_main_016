package com.codestates.edusync.model.study.plancalendar.mapper;

import com.codestates.edusync.model.common.dto.DateRangeDto;
import com.codestates.edusync.model.common.dto.TimeRangeDto;
import com.codestates.edusync.model.study.plancalendar.dto.*;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface CalendarMapper {

    @Named("PostDtoToEntity")
    @Mapping(source = "studyTimeStart", target = "time.studyTimeStart")
    @Mapping(source = "studyTimeEnd", target = "time.studyTimeEnd")
    TimeSchedule timeSchedulePostDtoToTimeSchedule(CalendarStudygroupDto.TimeScheduleDto.Post postStudygroupDto);

    @IterableMapping(qualifiedByName = "PostDtoToEntity")
    List<TimeSchedule> timeSchedulePostDtoListToTimeScheduleList(List<CalendarStudygroupDto.TimeScheduleDto.Post> timeSchedules);

    @Mapping(source = "studyTimeStart", target = "time.studyTimeStart")
    @Mapping(source = "studyTimeEnd", target = "time.studyTimeEnd")
    TimeSchedule timeSchedulePatchDtoToTimeSchedule(CalendarStudygroupDto.TimeScheduleDto.Patch patchDto);

    @Named("EntityToResponse")
    @Mapping(source = "time.studyTimeStart", target = "studyTimeStart")
    @Mapping(source = "time.studyTimeEnd", target = "studyTimeEnd")
    TimeScheduleSingleResponseDto timeScheduleListToTimeScheduleResponseDto(TimeSchedule timeSchedules);

    @IterableMapping(qualifiedByName = "EntityToResponse")
    List<TimeScheduleSingleResponseDto> timeScheduleListToTimeScheduleResponseDto(List<TimeSchedule> timeSchedules);

    default TimeScheduleResponseDto timeScheduleToTimeScheduleResponseDto(TimeSchedule ts) {
        TimeScheduleResponseDto result = new TimeScheduleResponseDto();
        result.setId(ts.getId());
        result.setStudyName(ts.getStudygroup().getStudyName());
        result.setPlatform(ts.getStudygroup().getPlatform());

        setDateAndTimeIntoTimeScheduleResponseDto(ts, result);

        return result;
    }

    static void setDateAndTimeIntoTimeScheduleResponseDto(TimeSchedule ts, TimeScheduleResponseDto result) {
        TimeRangeDto.Response resultTR = new TimeRangeDto.Response();
        resultTR.setStudyTimeStart(ts.getTime().getStudyTimeStart());
        resultTR.setStudyTimeEnd(ts.getTime().getStudyTimeEnd());
        result.setTimeScheduleInfo(resultTR);

        DateRangeDto.OnlyPeriodResponse resultDR = new DateRangeDto.OnlyPeriodResponse();
        resultDR.setStudyPeriodStart(ts.getStudygroup().getDate().getStudyPeriodStart());
        resultDR.setStudyPeriodEnd(ts.getStudygroup().getDate().getStudyPeriodEnd());
        result.setCalendarInfo(resultDR);
    }


    @Mapping(source = "studyTimeStart", target = "time.studyTimeStart")
    @Mapping(source = "studyTimeEnd", target = "time.studyTimeEnd")
    TimeSchedule memberTimeSchedulePostDtoToTimeSchedule(CalendarMemberDto.TimeScheduleDto.Post postMemberDto);

    @Mapping(source = "studyTimeStart", target = "time.studyTimeStart")
    @Mapping(source = "studyTimeEnd", target = "time.studyTimeEnd")
    TimeSchedule memberTimeSchedulePatchDtoToTimeSchedule(CalendarMemberDto.TimeScheduleDto.Patch patchDto);

    default CalendarStudygroupResponseDto timeScheduleToCalendarStudygroupResponseDto(TimeSchedule ts) {
        CalendarStudygroupResponseDto result = new CalendarStudygroupResponseDto();

        result.setCalendarId(ts.getId());
        result.setGroupId(ts.getStudygroup().getId());

        result.setTitle(ts.getTitle());
        result.setAllDay(false);

        DateRangeDto.OnlyPeriodResponse resultCalendar = new DateRangeDto.OnlyPeriodResponse();
        resultCalendar.setStudyPeriodStart(ts.getStudygroup().getDate().getStudyPeriodStart());
        resultCalendar.setStudyPeriodEnd(ts.getStudygroup().getDate().getStudyPeriodEnd());
        result.setCalendar(resultCalendar);

        TimeRangeDto.Response resultSchedule = new TimeRangeDto.Response();
        resultSchedule.setStudyTimeStart(ts.getTime().getStudyTimeStart());
        resultSchedule.setStudyTimeEnd(ts.getTime().getStudyTimeEnd());
        result.setSchedule(resultSchedule);

        result.setPlatform(ts.getStudygroup().getPlatform());
        result.setDescription(ts.getStudygroup().getIntroduction());
        result.setOverlap(true);

        Map<String, String> resultExtendedProps = new HashMap<>();
        resultExtendedProps.put("department", ts.getStudygroup().getStudyName());
        result.setExtendedProps(resultExtendedProps);

        result.setColor(ts.getColor());

        return result;
    }

    default CalendarMemberResponseDto timeScheduleToCalendarMemberResponseDto(TimeSchedule ts) {
        CalendarMemberResponseDto result = new CalendarMemberResponseDto();

        result.setCalendarId(ts.getId());

        result.setTitle(ts.getTitle());
        result.setAllDay(false);

        TimeRangeDto.Response resultSchedule = new TimeRangeDto.Response();
        resultSchedule.setStudyTimeStart(ts.getTime().getStudyTimeStart());
        resultSchedule.setStudyTimeEnd(ts.getTime().getStudyTimeEnd());
        result.setSchedule(resultSchedule);

        result.setPlatform(ts.getPlatform());
        result.setDescription(ts.getDescription());
        result.setOverlap(true);

        result.setColor(ts.getColor());

        return result;
    }
}
