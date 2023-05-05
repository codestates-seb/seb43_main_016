package com.codestates.edusync.study.postcomment.entity;

import com.codestates.edusync.audit.Auditable;
import com.codestates.edusync.study.classmate.entity.Classmate;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class StudyPostComment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "fk_studygroup_id")
    private Studygroup studygroup;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "fk_classmate_id")
    private Classmate classmate;
}
