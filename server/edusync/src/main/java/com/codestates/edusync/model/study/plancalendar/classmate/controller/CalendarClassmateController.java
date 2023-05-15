package com.codestates.edusync.model.study.plancalendar.classmate.controller;

import com.codestates.edusync.model.study.plancalendar.classmate.mapper.CalendarClassmateMapper;
import com.codestates.edusync.model.study.plancalendar.classmate.service.CalendarClassmateService;
import com.codestates.edusync.model.study.plancalendar.studygroup.dto.CalendarDto;
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
public class CalendarClassmateController {
    private final CalendarClassmateService calendarClassmateService;
    private final CalendarClassmateMapper mapper;

    private static final String DEFAULT_CALENDAR_URL = "/calendar";

    @PatchMapping(DEFAULT_CALENDAR_URL + "/{calendar-id}/classmate")
    public ResponseEntity patchCalendarClassmate(@PathVariable("calendar-id") @Positive Long calendarId,
                                                 @Valid @RequestBody CalendarDto.Patch patchDto) {

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(DEFAULT_CALENDAR_URL + "/{calendar-id}/classmate")
    public ResponseEntity deleteCalendarClassmate(@PathVariable("calendar-id") @Positive Long calendarId) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
