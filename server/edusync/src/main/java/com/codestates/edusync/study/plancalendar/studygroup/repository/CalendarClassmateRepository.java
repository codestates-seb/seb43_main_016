package com.codestates.edusync.study.plancalendar.studygroup.repository;

import com.codestates.edusync.study.classmate.entity.Classmate;
import com.codestates.edusync.study.plancalendar.studygroup.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarClassmateRepository extends JpaRepository<Calendar<Classmate>, Long> {

}
