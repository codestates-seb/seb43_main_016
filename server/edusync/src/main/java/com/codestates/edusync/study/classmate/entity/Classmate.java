package com.codestates.edusync.study.classmate.entity;

import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.study.locationInfo.entity.LocationInfo;
import com.codestates.edusync.study.plancalendar.classmate.entity.CalendarClassmate;
import com.codestates.edusync.study.postcomment.entity.StudyPostComment;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import com.codestates.edusync.study.studygroup.entity.StudygroupClassmateRef;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "fk_study_group_id")
    private Studygroup studygroup;

    @OneToMany(mappedBy = "classmate", fetch = LAZY)
    @JoinColumn(name = "fk_studygroup_classmate_refs")
    private List<StudygroupClassmateRef> studygroupClassmateRef;

    @OneToOne(cascade = {MERGE}, fetch = EAGER)
    @JoinColumn(name = "fk_classmate_id")
    private Member member;

    @OneToMany(cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    @JoinColumn(name = "fk_study_post_comments_id")
    private List<StudyPostComment> studyPostComments = new ArrayList<>();

    @OneToOne(cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    @JoinColumn(name = "fk_calendar_classmate_id")
    private CalendarClassmate calendarClassmate;
}
