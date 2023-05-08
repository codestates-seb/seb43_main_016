package com.codestates.edusync.study.plancalendar.studygroup.controller;

import com.codestates.edusync.study.plancalendar.studygroup.dto.CalendarDto;
import com.codestates.edusync.study.plancalendar.studygroup.mapper.CalendarMapper;
import com.codestates.edusync.study.plancalendar.studygroup.service.CalendarClassmateService;
import com.codestates.edusync.study.classmate.entity.Classmate;
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
    private final CalendarMapper<Classmate> mapper;

    private static final String DEFAULT_CALENDAR_URL = "/calendar";

    @PatchMapping(DEFAULT_CALENDAR_URL + "/{calendar-id}")
    public ResponseEntity patchCalendarClassmate(@PathVariable("calendar-id") @Positive Long calendarId,
                                                 @Valid @RequestBody CalendarDto.Patch patchDto) {

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(DEFAULT_CALENDAR_URL + "/{calendar-id}")
    public ResponseEntity deleteCalendarClassmate(@PathVariable("calendar-id") @Positive Long calendarId) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
