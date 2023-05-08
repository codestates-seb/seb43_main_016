package com.codestates.edusync.study.plancalendar.classmate.service;

import com.codestates.edusync.study.plancalendar.classmate.repository.CalendarClassmateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CalendarClassmateService {
    private final CalendarClassmateRepository calendarClassmateRepository;

}
