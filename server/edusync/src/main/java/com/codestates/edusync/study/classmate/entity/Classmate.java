package com.codestates.edusync.study.classmate.entity;

import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.infodto.locationinfo.entity.LocationInfo;
import com.codestates.edusync.study.plancalendar.classmate.entity.CalendarClassmate;
import com.codestates.edusync.study.postcomment.entity.StudyPostComment;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import com.codestates.edusync.study.studygroup.entity.StudygroupClassmateRef;
import com.codestates.edusync.study.studyjoin.entity.StudygroupJoin;
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
public class Classmate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {PERSIST, MERGE, CascadeType.REMOVE}, fetch = LAZY)
    @JoinColumn(name = "fk_location_info_id")
    private LocationInfo locationInfo;

    @OneToMany(mappedBy = "studygroup", cascade = {PERSIST, REMOVE}, fetch = LAZY)
    private List<StudygroupJoin> studygroupJoins = new ArrayList<>();

    @OneToMany(mappedBy = "studygroupLeader", fetch = LAZY)
    private List<Studygroup> studygroupAsLeader = new ArrayList<>();

    @OneToMany(mappedBy = "classmate", fetch = LAZY)
    private List<StudygroupClassmateRef> studygroupClassmateRef = new ArrayList<>();

    @OneToOne(cascade = {MERGE}, fetch = EAGER)
    @JoinColumn(name = "fk_classmate_id")
    private Member member;

    @OneToMany(mappedBy = "classmate", cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    private List<StudyPostComment> studyPostComments = new ArrayList<>();

    @OneToOne(mappedBy = "classmate", cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    private CalendarClassmate calendarClassmate;
}
