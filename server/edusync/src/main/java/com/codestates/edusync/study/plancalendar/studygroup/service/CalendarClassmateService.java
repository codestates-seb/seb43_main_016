package com.codestates.edusync.study.plancalendar.studygroup.service;

import com.codestates.edusync.study.plancalendar.studygroup.repository.CalendarClassmateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CalendarClassmateService {
    private final CalendarClassmateRepository calendarClassmateRepository;

}
