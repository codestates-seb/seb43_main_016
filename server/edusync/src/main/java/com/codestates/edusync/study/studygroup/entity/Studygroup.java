package com.codestates.edusync.study.studygroup.entity;

import com.codestates.edusync.audit.Auditable;
import com.codestates.edusync.study.classmate.entity.Classmate;
import com.codestates.edusync.study.locationInfo.entity.LocationInfo;
import com.codestates.edusync.study.plancalendar.studygroup.entity.CalendarStudygroup;
import com.codestates.edusync.study.postcomment.entity.StudyPostComment;
import com.codestates.edusync.tags.entity.SearchTag;
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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String introduction;

    @OneToOne(cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    @JoinColumn(name = "fk_location_info_id")
    private LocationInfo groupLocation;

    @ManyToOne(cascade = {PERSIST, MERGE}, fetch = EAGER)
    @JoinColumn(name = "fk_studygroup_leader_id")
    private Classmate studygroupLeader;

    @OneToMany(mappedBy = "studygroup", fetch = LAZY)
    @JoinColumn(name = "fk_studygroup_classmate_refs")
    private List<StudygroupClassmateRef> studygroupClassmateRefs = new ArrayList<>();

    // 댓글은 항상 필요한 것이니 바로 불러올 수 있도록 한다
    @OneToMany(cascade = {REMOVE}, fetch = EAGER)
    @JoinColumn(name = "fk_study_post_comments_id")
    private List<StudyPostComment> studyPostComments = new ArrayList<>();

    @OneToOne(cascade = {PERSIST, MERGE, REMOVE}, fetch = EAGER)
    @JoinColumn(name = "fk_calendar_studygroup_id")
    private CalendarStudygroup calendarStudygroup;

    @OneToMany(cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    @JoinColumn(name = "fk_search_tags_id")
    private List<SearchTag> searchTags = new ArrayList<>();
}
