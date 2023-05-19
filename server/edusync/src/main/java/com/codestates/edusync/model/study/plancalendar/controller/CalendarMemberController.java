package com.codestates.edusync.model.study.plancalendar.controller;

import com.codestates.edusync.model.common.dto.TimeRangeDto;
import com.codestates.edusync.model.study.plancalendar.dto.CalendarMemberDto;
import com.codestates.edusync.model.study.plancalendar.dto.CalendarMemberResponseDto;
import com.codestates.edusync.model.study.plancalendar.dto.TimeScheduleResponseDto;
import com.codestates.edusync.model.study.plancalendar.mapper.CalendarMapper;
import com.codestates.edusync.model.study.plancalendar.service.CalendarMemberService;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
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

    /**
     * <h2>개인 캘린더 단일 생성</h2>
     * 스터디에 속해 있지 않은 일정을 개인적으로 생성<br>
     * @param postDto        생성할 단일 일정
     * @param authentication
     * @return 응답 코드
     */
    @PostMapping(DEFAULT_MEMBER_URL)
    public ResponseEntity postCalendarMember(@Valid @RequestBody CalendarMemberDto.Post postDto,
                                             Authentication authentication) {
        calendarMemberService.createTimeSchedulesExceptStudygroup(
                mapper.memberTimeSchedulePostDtoToTimeSchedule(postDto.getTimeSchedule()),
                authentication.getPrincipal().toString()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * <h2>개인 캘린더 단일 수정</h2>
     * 스터디의 일정은 수정 불가능.<br>
     * 개인이 생성한 것만 수정 가능<br>
     * @param timeScheduleId 수정할 일정
     * @param patchDto       수정할 내용이 담긴 DTO
     * @param authentication
     * @return 응답 코드
     */
    @PatchMapping("/{timeschedule-id}" + DEFAULT_MEMBER_URL)
    public ResponseEntity patchCalendarMember(@PathVariable("timeschedule-id") @Positive Long timeScheduleId,
                                              @Valid @RequestBody CalendarMemberDto.Patch patchDto,
                                              Authentication authentication) {


        calendarMemberService.updateTimeSchedule(
                timeScheduleId,
                mapper.memberTimeSchedulePatchDtoToTimeSchedule(patchDto.getTimeSchedule()),
                authentication.getPrincipal().toString()
        );

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * <h2>개인(본인) 캘린더 리스트 조회</h2>
     * 스터디의 리스트가 포함된, 개인의 일정을 조회
     * @param authentication
     * @return 일정표 리스트
     */
    @GetMapping(DEFAULT_MEMBER_URL)
    public ResponseEntity getAllTimeScheduleOfMember(Authentication authentication) {

        List<TimeSchedule> findTimeSchedules =
                calendarMemberService.getTimeSchedules(authentication.getPrincipal().toString());

        List<TimeRangeDto.Response> responseDtos =
                mapper.timeScheduleListToTimeScheduleResponseDto(findTimeSchedules);

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    /**
     * <h2>개인 캘린더 단일 조회</h2>
     * 상세 조회<br>
     * 스터디의 일정과는 응답 형식은 같으나, 내용을 다르게 설정할 수도 있음<br>
     * @param timeScheduleId 일정 시간표
     * @return 상세 조회 내용
     */
    @GetMapping("/{timeschedule-id}" + DEFAULT_MEMBER_URL)
    public ResponseEntity getSingleTimeSchedule(@PathVariable("timeschedule-id") @Positive Long timeScheduleId) {
        TimeSchedule findTimeSchedule =
                calendarMemberService.getSingleTimeScheduleByTimeScheduleId(
                        timeScheduleId
                );

        CalendarMemberResponseDto responseDto =
                mapper.timeScheduleToCalendarMemberResponseDto(findTimeSchedule);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * <h2>개인 캘린더 단일 삭제</h2>
     * 스터디의 일정은 삭제 불가능<br>
     * 개인이 생성한 것만 삭제 가능<br>
     * @param timeScheduleId 일정 시간
     * @param authentication
     * @return 응답 코드
     */
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
