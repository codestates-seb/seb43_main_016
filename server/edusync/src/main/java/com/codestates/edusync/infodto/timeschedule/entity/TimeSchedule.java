package com.codestates.edusync.infodto.timeschedule.entity;

import com.codestates.edusync.audit.Auditable;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class TimeSchedule extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Timestamp startTime;

    @Column
    private Timestamp endTime;

    @ManyToOne
    @JoinColumn(name = "fk_studygroup_id")
    private Studygroup studygroup;
}
