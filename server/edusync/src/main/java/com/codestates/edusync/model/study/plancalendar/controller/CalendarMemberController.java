package com.codestates.edusync.model.study.plancalendar.controller;

import com.codestates.edusync.model.common.dto.TimeRangeDto;
import com.codestates.edusync.model.study.plancalendar.dto.TimeScheduleResponseDto;
import com.codestates.edusync.model.study.plancalendar.mapper.CalendarMemberMapper;
import com.codestates.edusync.model.study.plancalendar.service.CalendarMemberService;
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
public class CalendarMemberController {
    private final CalendarMemberService calendarMemberService;
    private final CalendarMemberMapper mapper;

    private static final String DEFAULT_MEMBER_URL = "/members";

    @PostMapping("/{timeschedule-id}" + DEFAULT_MEMBER_URL)
    public ResponseEntity postCalendarMember(@PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                             @Valid @RequestBody CalendarDto.List listDto,
                                             Authentication authentication) {
        calendarMemberService.createTimeSchedulesExceptStudygroup(
                mapper.timeSchedulePostDtoListToTimeScheduleList(listDto.getTimeSchedules()),
                authentication.getPrincipal().toString()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(DEFAULT_MEMBER_URL + "/{member-uuid}" + DEFAULT_TIME_SCHEDULE_URL + "/{timeschedule-id}")
    public ResponseEntity patchCalendarMember(@PathVariable("member-uuid") @Positive String memberUuid,
                                                  @PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                                  @Valid @RequestBody CalendarDto.Single singleDto,
                                                  Authentication authentication) {
        calendarMemberService.updateTimeSchedule(
                memberUuid, timeScheduleId,
                mapper.timeSchedulePatchDtoToTimeSchedule(singleDto),
                authentication.getPrincipal().toString()
        );

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(DEFAULT_MEMBER_URL + "/{member-uuid}" + DEFAULT_TIME_SCHEDULE_URL + "s")
    public ResponseEntity getAllTimeScheduleOfMember(@PathVariable("member-uuid") @Positive String memberUuid) {

        List<TimeSchedule> findTimeSchedules =
                calendarMemberService.getTimeSchedules(memberUuid);

        List<TimeRangeDto.Response> responseDtos =
                mapper.timeScheduleListToTimeScheduleResponseDto(findTimeSchedules);

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping(DEFAULT_MEMBER_URL + "/{member-uuid}" + DEFAULT_TIME_SCHEDULE_URL + "/{timeschedule-id}")
    public ResponseEntity getTimeScheduleOfMember(@PathVariable("member-uuid") @Positive String memberUuid,
                                                      @PathVariable("timeschedule-id") @Positive Long timeScheduleId) {
        TimeSchedule findTimeSchedule =
                calendarMemberService.getSingleTimeScheduleByTimeScheduleId(
                        memberUuid, timeScheduleId
                );

        TimeScheduleResponseDto responseDto =
                mapper.timeScheduleToTimeScheduleResponseDto(findTimeSchedule);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping(DEFAULT_MEMBER_URL + "/{member-uuid}" + DEFAULT_TIME_SCHEDULE_URL + "/{timeschedule-id}")
    public ResponseEntity deleteCalendarMember(@PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                               Authentication authentication) {
        calendarMemberService.deleteTimeScheduleByTimeScheduleId(
                timeScheduleId,
                authentication.getPrincipal().toString()
        );

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
