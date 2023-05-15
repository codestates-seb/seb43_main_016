package com.codestates.edusync.model.study.plancalendar.studygroup.controller;

import com.codestates.edusync.model.common.service.MemberUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.plancalendar.dto.TimeScheduleResponseDto;
import com.codestates.edusync.model.study.plancalendar.studygroup.mapper.CalendarStudygroupMapper;
import com.codestates.edusync.model.study.plancalendar.studygroup.service.CalendarStudygroupService;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.studygroup.dto.CalendarDto;
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
@RestController
public class CalendarStudygroupController {
    private final CalendarStudygroupService calendarStudygroupService;
    private final CalendarStudygroupMapper mapper;
    private final MemberUtils memberUtils;

    private static final String DEFAULT_STUDYGROUP_URL = "/studygroup";
    private static final String DEFAULT_TIME_SCHEDULE_URL = "/timeSchedule";

    @PostMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_TIME_SCHEDULE_URL)
    public ResponseEntity postCalendarStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                 @Valid @RequestBody CalendarDto.Post postDto,
                                                 Authentication authentication) {
        Member loginMember = memberUtils.getLoggedIn(authentication);
        calendarStudygroupService.createTimeSchedulesForStudygroup(
                studygroupId,
                mapper.timeSchedulePostDtoListToTimeScheduleList(postDto.getTimeSchedules()),
                loginMember
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_TIME_SCHEDULE_URL + "/{timeschedule-id}")
    public ResponseEntity patchCalendarStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                  @PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                                  @Valid @RequestBody CalendarDto.Patch patchDto,
                                                  Authentication authentication) {
        Member loginMember = memberUtils.getLoggedIn(authentication);

        calendarStudygroupService.updateStudygroupTimeSchedule(
                studygroupId, timeScheduleId,
                mapper.timeSchedulePatchDtoToTimeSchedule(patchDto),
                loginMember
        );

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_TIME_SCHEDULE_URL + "s")
    public ResponseEntity getAllTimeScheduleOfStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId) {

        List<TimeSchedule> findTimeSchedules =
                calendarStudygroupService.getTimeSchedulesByStudygroupId(studygroupId);

        List<TimeScheduleResponseDto.TimeScheduleDto> responseDtos =
                mapper.timeScheduleListToTimeScheduleResponseDto(findTimeSchedules);

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_TIME_SCHEDULE_URL + "/{timeschedule-id}")
    public ResponseEntity getTimeScheduleOfStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                      @PathVariable("timeschedule-id") @Positive Long timeScheduleId) {
        TimeSchedule findTimeSchedule =
                calendarStudygroupService.getSingleTimeScheduleById(studygroupId, timeScheduleId);

        TimeScheduleResponseDto responseDto =
                mapper.timeScheduleToTimeScheduleResponseDto(findTimeSchedule);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_TIME_SCHEDULE_URL + "/{timeschedule-id}")
    public ResponseEntity deleteCalendarStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                   @PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                                   Authentication authentication) {
        Member loginMember = memberUtils.getLoggedIn(authentication);
        calendarStudygroupService.deleteTimeScheduleByTimeScheduleId(studygroupId, timeScheduleId, loginMember);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
