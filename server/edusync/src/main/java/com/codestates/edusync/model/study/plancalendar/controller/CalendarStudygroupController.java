package com.codestates.edusync.model.study.plancalendar.controller;

import com.codestates.edusync.model.common.dto.TimeRangeDto;
import com.codestates.edusync.model.study.plancalendar.dto.TimeScheduleResponseDto;
import com.codestates.edusync.model.study.plancalendar.mapper.CalendarStudygroupMapper;
import com.codestates.edusync.model.study.plancalendar.service.CalendarStudygroupService;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.dto.CalendarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequiredArgsConstructor
@Validated
@RequestMapping("/calendars")
@RestController
public class CalendarStudygroupController {
    private final CalendarStudygroupService calendarStudygroupService;
    private final CalendarStudygroupMapper mapper;

    private static final String DEFAULT_STUDYGROUP_URL = "/studygroups";

    @PostMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}")
    public ResponseEntity postCalendarStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                 @Valid @RequestBody CalendarDto.List listDto,
                                                 Authentication authentication) {
        calendarStudygroupService.createTimeSchedules(
                studygroupId,
                mapper.timeSchedulePostDtoListToTimeScheduleList(listDto.getTimeSchedules()),
                authentication.getPrincipal().toString()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{timeschedule-id}" + DEFAULT_STUDYGROUP_URL + "/{studygroup-id}")
    public ResponseEntity patchCalendarStudygroup(@PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                                  @PathVariable("studygroup-id") @Positive Long studygroupId,
                                                  @Valid @RequestBody CalendarDto.Single singleDto,
                                                  Authentication authentication) {
        calendarStudygroupService.updateTimeSchedule(
                studygroupId, timeScheduleId,
                mapper.timeSchedulePatchDtoToTimeSchedule(singleDto),
                authentication.getPrincipal().toString()
        );

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}")
    public ResponseEntity getAllTimeScheduleOfStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId) {

        List<TimeSchedule> findTimeSchedules =
                calendarStudygroupService.getTimeSchedules(studygroupId);

        List<TimeRangeDto.Response> responseDtos =
                mapper.timeScheduleListToTimeScheduleResponseDto(findTimeSchedules);

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/{timeschedule-id}" + DEFAULT_STUDYGROUP_URL + "/{studygroup-id}")
    public ResponseEntity getTimeScheduleOfStudygroup(@PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                                      @PathVariable("studygroup-id") @Positive Long studygroupId) {
        TimeSchedule findTimeSchedule =
                calendarStudygroupService.getSingleTimeScheduleByTimeScheduleId(
                        studygroupId, timeScheduleId
                );

        TimeScheduleResponseDto responseDto =
                mapper.timeScheduleToTimeScheduleResponseDto(findTimeSchedule);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{timeschedule-id}" + DEFAULT_STUDYGROUP_URL + "/{studygroup-id}")
    public ResponseEntity deleteCalendarStudygroup(@PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                                   @PathVariable("studygroup-id") @Positive Long studygroupId,
                                                   Authentication authentication) {
        calendarStudygroupService.deleteTimeScheduleByTimeScheduleId(
                studygroupId, timeScheduleId,
                authentication.getPrincipal().toString()
        );

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
