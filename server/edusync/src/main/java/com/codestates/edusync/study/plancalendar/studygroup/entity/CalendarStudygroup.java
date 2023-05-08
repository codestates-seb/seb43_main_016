package com.codestates.edusync.study.plancalendar.studygroup.entity;

import com.codestates.edusync.infodto.timeschedule.entity.TimeSchedule;
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
    private Studygroup studygroup;

    @OneToMany(cascade = {PERSIST, MERGE, REMOVE}, fetch = EAGER)
    @JoinColumn(name = "fk_time_schedules_id")
    private List<TimeSchedule> timeSchedules = new ArrayList<>();
}
