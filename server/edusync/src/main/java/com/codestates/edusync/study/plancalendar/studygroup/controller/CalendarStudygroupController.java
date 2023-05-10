package com.codestates.edusync.study.plancalendar.studygroup.controller;

import com.codestates.edusync.study.plancalendar.studygroup.dto.CalendarDto;
import com.codestates.edusync.study.plancalendar.studygroup.mapper.CalendarStudygroupMapper;
import com.codestates.edusync.study.plancalendar.studygroup.service.CalendarStudygroupService;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Validated
@RestController
public class CalendarStudygroupController {
    private final CalendarStudygroupService calendarStudygroupService;
    private final CalendarStudygroupMapper mapper;

    private static final String DEFAULT_CALENDAR_URL = "/calendar";

    @PatchMapping(DEFAULT_CALENDAR_URL + "/{calendar-id}/studygroup")
    public ResponseEntity patchCalendarStudygroup(@PathVariable("calendar-id") @Positive Long calendarId,
                                                  @Valid @RequestBody CalendarDto.Patch patchDto) {

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(DEFAULT_CALENDAR_URL + "/{calendar-id}/studygroup")
    public ResponseEntity deleteCalendarStudygroup(@PathVariable("calendar-id") @Positive Long calendarId) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
