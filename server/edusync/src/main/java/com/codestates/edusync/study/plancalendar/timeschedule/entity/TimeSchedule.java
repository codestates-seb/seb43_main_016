package com.codestates.edusync.study.plancalendar.timeschedule.entity;

import com.codestates.edusync.audit.Auditable;
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

    @Column(length = 100, nullable = false)
    private String content;

    @Column
    private Timestamp startTime;

    @Column
    private Timestamp endTime;
}
