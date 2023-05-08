package com.codestates.edusync.study.studygroup.entity;

import com.codestates.edusync.audit.Auditable;
import com.codestates.edusync.infodto.calendarinfo.entity.CalendarInfo;
import com.codestates.edusync.study.classmate.entity.Classmate;
import com.codestates.edusync.infodto.locationinfo.entity.LocationInfo;
import com.codestates.edusync.study.plancalendar.studygroup.entity.CalendarStudygroup;
import com.codestates.edusync.infodto.timeschedule.entity.TimeSchedule;
import com.codestates.edusync.study.postcomment.entity.StudyPostComment;
import com.codestates.edusync.study.studyjoin.entity.StudygroupJoin;
import com.codestates.edusync.searchtag.entity.SearchTag;
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
public class Studygroup extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String studyName;

    @OneToOne(cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    @JoinColumn(name = "fk_calendar_info_id")
    private CalendarInfo calendar;

    @OneToMany(mappedBy = "studygroup", cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    private List<TimeSchedule> timeSchedules = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "TEXT")
    private String introduction;

    @Column
    private Integer maxClassmateCount;

    @Column(length = 50, nullable = false)
    private String platform;

    @OneToMany(mappedBy = "studygroup", cascade = {PERSIST, REMOVE}, fetch = LAZY)
    private List<StudygroupJoin> studygroupJoins = new ArrayList<>();

    @OneToOne(cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    @JoinColumn(name = "fk_location_info_id")
    private LocationInfo groupLocation;

    @ManyToOne(cascade = {PERSIST, MERGE}, fetch = EAGER)
    @JoinColumn(name = "fk_studygroup_leader_id")
    private Classmate studygroupLeader;

    @OneToMany(mappedBy = "studygroup", fetch = LAZY)
    private List<Classmate> classmates = new ArrayList<>();

    @OneToMany(mappedBy = "studygroup", cascade = {REMOVE}, fetch = LAZY)
    private List<StudyPostComment> studyPostComments = new ArrayList<>();

    @OneToOne(mappedBy = "studygroup", cascade = {PERSIST, MERGE, REMOVE}, fetch = EAGER)
    private CalendarStudygroup calendarStudygroup;

    @OneToMany(mappedBy = "studygroup", cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    private List<SearchTag> tags = new ArrayList<>();
}
