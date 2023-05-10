package com.codestates.edusync.infodto.timeschedule.entity;

import com.codestates.edusync.audit.Auditable;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class TimeSchedule extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(length = 200)
    private String content;

    @Column
    private Timestamp start;
    @Column
    private Timestamp end;

    @ManyToOne(fetch = LAZY, cascade = {PERSIST, MERGE, REMOVE})
    @JoinColumn(name = "studygroup_id")
    private Studygroup studygroup;

    @ManyToOne(fetch = LAZY, cascade = {PERSIST, MERGE, REMOVE})
    @JoinColumn(name = "member_id")
    private Member member;

    public void setStudygroup(Studygroup studygroup) {
        if (studygroup == null) {
            throw new IllegalArgumentException("Studygroup cannot be null");
        }

        if(this.studygroup != null) {
            this.studygroup.getTimeSchedules().remove(this);
        }
        this.studygroup = studygroup;
        this.studygroup.getTimeSchedules().add(this);
    }

    public void setMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        if(this.member != null) {
            this.member.getTimeSchedules().remove(this);
        }
        this.member = member;
        this.member.getTimeSchedules().add(this);
    }

}
