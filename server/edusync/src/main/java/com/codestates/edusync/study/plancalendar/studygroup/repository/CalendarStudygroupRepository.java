package com.codestates.edusync.study.plancalendar.studygroup.repository;

import com.codestates.edusync.study.plancalendar.studygroup.entity.Calendar;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarStudygroupRepository extends JpaRepository<Calendar<Studygroup>, Long> {

}
