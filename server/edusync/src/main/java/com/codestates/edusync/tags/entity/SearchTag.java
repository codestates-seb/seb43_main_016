package com.codestates.edusync.tags.entity;

import com.codestates.edusync.study.studygroup.entity.Studygroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class SearchTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String tagKey;

    @Column(length = 100)
    private String tagValue;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "fk_studygroup_id")
    private Studygroup studygroup;
}
