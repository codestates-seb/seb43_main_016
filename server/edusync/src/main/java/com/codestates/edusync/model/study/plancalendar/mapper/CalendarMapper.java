package com.codestates.edusync.model.study.plancalendar.mapper;

import com.codestates.edusync.model.common.dto.DateRangeDto;
import com.codestates.edusync.model.common.dto.TimeRangeDto;
import com.codestates.edusync.model.study.plancalendar.dto.CalendarMemberDto;
import com.codestates.edusync.model.study.plancalendar.dto.TimeScheduleResponseDto;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.dto.CalendarStudygroupDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarMapper {

    @Named("PostDtoToEntity")
    @Mapping(source = "studyTimeStart", target = "time.studyTimeStart")
    @Mapping(source = "studyTimeEnd", target = "time.studyTimeEnd")
    TimeSchedule timeSchedulePostDtoToTimeSchedule(CalendarStudygroupDto.TimeScheduleDto.Post postStudygroupDto);

    @IterableMapping(qualifiedByName = "PostDtoToEntity")
    List<TimeSchedule> timeSchedulePostDtoListToTimeScheduleList(List<CalendarStudygroupDto.TimeScheduleDto.Post> timeSchedules);

    @Mapping(source = "timeSchedule.studyTimeStart", target = "time.studyTimeStart")
    @Mapping(source = "timeSchedule.studyTimeEnd", target = "time.studyTimeEnd")
    TimeSchedule timeSchedulePatchDtoToTimeSchedule(CalendarStudygroupDto.Patch patchDto);

    @Named("EntityToResponse")
    @Mapping(source = "time.studyTimeStart", target = "studyTimeStart")
    @Mapping(source = "time.studyTimeEnd", target = "studyTimeEnd")
    TimeRangeDto.Response timeScheduleListToTimeScheduleResponseDto(TimeSchedule timeSchedules);

    @IterableMapping(qualifiedByName = "EntityToResponse")
    List<TimeRangeDto.Response> timeScheduleListToTimeScheduleResponseDto(List<TimeSchedule> timeSchedules);

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

    @Mapping(source = "timeSchedule.studyTimeStart", target = "time.studyTimeStart")
    @Mapping(source = "timeSchedule.studyTimeEnd", target = "time.studyTimeEnd")
    TimeSchedule memberTimeSchedulePatchDtoToTimeSchedule(CalendarMemberDto.Patch patchDto);
}
