package com.codestates.edusync.model.study.plancalendar.repository;

import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarStudygroupRepository extends JpaRepository<TimeSchedule, Long> {

    List<TimeSchedule> findAllByStudygroupId(Long studygroupId);
}
