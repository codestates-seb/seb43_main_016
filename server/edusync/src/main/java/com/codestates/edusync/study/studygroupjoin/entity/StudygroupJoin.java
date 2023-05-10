package com.codestates.edusync.study.studygroupjoin.entity;

import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class StudygroupJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_approved")
    private Boolean isApproved = false;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "studygroup_join_member_id")
    private Member member;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "studygroup_join_studygroup_id")
    private Studygroup studygroup;
}
