package com.codestates.edusync.study.studygroup.entity;

import com.codestates.edusync.audit.Auditable;
import com.codestates.edusync.infodto.timeschedule.entity.TimeSchedule;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.study.postcomment.entity.StudygroupPostComment;
import com.codestates.edusync.study.studygroupjoin.entity.StudygroupJoin;
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

    @Column(length = -1, name = "studygroup_image")
    private String studygroupImage;

    @Column(length = 200)
    private String address;

    @Column(length = 100, name = "study_period")
    private String studyPeriod;

    @Column(length = 100, name = "study_time")
    private String studyTime;

    @OneToMany(mappedBy = "studygroup", fetch = LAZY)
    private List<TimeSchedule> timeSchedules = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "TEXT")
    private String introduction;

    @Column
    private Integer memberCountMin;

    @Column
    private Integer memberCountMax;

    @Column(length = 50, nullable = false)
    private String platform;

    @ManyToOne(cascade = {PERSIST, MERGE}, fetch = EAGER)
    @JoinColumn(name = "leader_member_id")
    private Member leaderMember;

    @OneToMany(mappedBy = "studygroup", fetch = LAZY)
    private List<StudygroupJoin> studygroupJoins = new ArrayList<>();

    @OneToMany(mappedBy = "studygroup", cascade = {REMOVE}, fetch = LAZY)
    private List<StudygroupPostComment> studygroupPostComments = new ArrayList<>();

    @OneToMany(mappedBy = "studygroup", cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    private List<SearchTag> tags = new ArrayList<>();
}
