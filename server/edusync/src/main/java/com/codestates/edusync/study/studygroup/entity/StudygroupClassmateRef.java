package com.codestates.edusync.study.studygroup.entity;

import com.codestates.edusync.study.classmate.entity.Classmate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class StudygroupClassmateRef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Classmate classmate;

    @ManyToOne
    private Studygroup studygroup;
}
