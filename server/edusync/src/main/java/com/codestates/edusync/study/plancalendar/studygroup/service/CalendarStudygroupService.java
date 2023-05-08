package com.codestates.edusync.study.plancalendar.studygroup.service;

import com.codestates.edusync.study.plancalendar.studygroup.repository.CalendarStudygroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CalendarStudygroupService {
    private final CalendarStudygroupRepository calendarStudygroupRepository;

}
