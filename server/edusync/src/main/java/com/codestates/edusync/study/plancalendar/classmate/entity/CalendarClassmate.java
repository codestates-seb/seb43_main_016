package com.codestates.edusync.study.plancalendar.classmate.entity;

import com.codestates.edusync.study.classmate.entity.Classmate;
import com.codestates.edusync.study.plancalendar.studygroup.entity.CalendarStudygroup;
import com.codestates.edusync.study.plancalendar.timeschedule.entity.TimeSchedule;
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
public class CalendarClassmate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fk_classmate_id")
    private Classmate classmate;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "fk_calendar_studygroup_id")
    private CalendarStudygroup calendarStudygroup;

    @OneToMany(cascade = {PERSIST, MERGE, REMOVE}, fetch = EAGER)
    @JoinColumn(name = "fk_time_schedules_id")
    private List<TimeSchedule> timeSchedules = new ArrayList<>();
}
