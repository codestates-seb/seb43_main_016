package com.codestates.edusync.model.study.plancalendar.controller;

import com.codestates.edusync.model.common.dto.TimeRangeDto;
import com.codestates.edusync.model.study.plancalendar.dto.CalendarMemberDto;
import com.codestates.edusync.model.study.plancalendar.dto.TimeScheduleResponseDto;
import com.codestates.edusync.model.study.plancalendar.mapper.CalendarMapper;
import com.codestates.edusync.model.study.plancalendar.service.CalendarMemberService;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.dto.CalendarStudygroupDto;
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
public class CalendarMemberController {
    private final CalendarMemberService calendarMemberService;
    private final CalendarMapper mapper;

    private static final String DEFAULT_MEMBER_URL = "/members";

    @PostMapping("/{timeschedule-id}" + DEFAULT_MEMBER_URL)
    public ResponseEntity postCalendarMember(@PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                             @Valid @RequestBody CalendarMemberDto.Post postDto,
                                             Authentication authentication) {
        calendarMemberService.createTimeSchedulesExceptStudygroup(
                mapper.memberTimeSchedulePostDtoToTimeSchedule(postDto.getTimeSchedule()),
                authentication.getPrincipal().toString()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{timeschedule-id}" + DEFAULT_MEMBER_URL)
    public ResponseEntity patchCalendarMember(@PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                              @Valid @RequestBody CalendarMemberDto.Patch patchDto,
                                              Authentication authentication) {
        calendarMemberService.updateTimeSchedule(
                timeScheduleId,
                mapper.memberTimeSchedulePatchDtoToTimeSchedule(patchDto),
                authentication.getPrincipal().toString()
        );

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{timeschedule-id}" + DEFAULT_MEMBER_URL)
    public ResponseEntity getAllTimeScheduleOfMember(@PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                                     Authentication authentication) {

        List<TimeSchedule> findTimeSchedules =
                calendarMemberService.getTimeSchedules(authentication.getPrincipal().toString());

        List<TimeRangeDto.Response> responseDtos =
                mapper.timeScheduleListToTimeScheduleResponseDto(findTimeSchedules);

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/{timeschedule-id}")
    public ResponseEntity getSingleTimeSchedule(@PathVariable("timeschedule-id") @Positive Long timeScheduleId) {
        TimeSchedule findTimeSchedule =
                calendarMemberService.getSingleTimeScheduleByTimeScheduleId(
                        timeScheduleId
                );

        TimeScheduleResponseDto responseDto =
                mapper.timeScheduleToTimeScheduleResponseDto(findTimeSchedule);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{timeschedule-id}" + DEFAULT_MEMBER_URL)
    public ResponseEntity deleteCalendarMember(@PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                               Authentication authentication) {
        calendarMemberService.deleteTimeScheduleByTimeScheduleId(
                timeScheduleId,
                authentication.getPrincipal().toString()
        );

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
