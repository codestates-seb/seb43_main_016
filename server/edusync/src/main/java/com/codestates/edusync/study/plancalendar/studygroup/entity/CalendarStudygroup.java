package com.codestates.edusync.study.plancalendar.studygroup.entity;

import com.codestates.edusync.study.plancalendar.classmate.entity.CalendarClassmate;
import com.codestates.edusync.study.plancalendar.timeschedule.entity.TimeSchedule;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class CalendarStudygroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {PERSIST, MERGE}, fetch = LAZY)
    @JoinColumn(name = "fk_studygroup_id")
    private Studygroup studygroup;

    // Studygroup 의 캘린더를 변경하거나 추가, 삭제할 경우, Classmate 의 캘린더에도 함께 적용한다
    @OneToMany(mappedBy = "calendarStudygroup", cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    private List<CalendarClassmate> calendarClassmates = new ArrayList<>();

    @OneToMany(cascade = {PERSIST, MERGE, REMOVE}, fetch = EAGER)
    @JoinColumn(name = "fk_time_schedules_id")
    private List<TimeSchedule> timeSchedules = new ArrayList<>();
}
