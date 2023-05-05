package com.codestates.edusync.study.studyjoin.entity;

import com.codestates.edusync.study.classmate.entity.Classmate;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class StudygroupJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_classmate_id")
    private Classmate classmate;

    @ManyToOne
    @JoinColumn(name = "fk_studygroup_id")
    private Studygroup studygroup;
}
