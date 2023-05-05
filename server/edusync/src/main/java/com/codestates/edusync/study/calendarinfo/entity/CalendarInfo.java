package com.codestates.edusync.study.calendarinfo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class CalendarInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp startTime;
    private Timestamp endTime;
}
