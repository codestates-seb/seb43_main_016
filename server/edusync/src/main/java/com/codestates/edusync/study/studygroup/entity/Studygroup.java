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

import java.sql.Timestamp;
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

    @Column(length = 50, name = "days_of_week")
    private String daysOfWeek;

    @Column(name = "study_period_start")
    private Timestamp studyPeriodStart;

    @Column(name = "study_period_end")
    private Timestamp studyPeriodEnd;

    @Column(name = "study_time_start")
    private Timestamp studyTimeStart;

    @Column(name = "study_time_end")
    private Timestamp studyTimeEnd;

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

    /**
     * <h2>양방향 매핑을 위한 One To Many 쪽의 Setter 설정</h2>
     * 기존에 연결되어있던 관계를 끊고, 양쪽 객체를 새로 연결해준다.
     * @param timeSchedules  새로 매핑할 TimeSchedule 의 리스트( 시간표 )
     */
    public void setTimeSchedules(List<TimeSchedule> timeSchedules) {
        if (timeSchedules == null) {
            throw new IllegalArgumentException("TimeSchedule List cannot be null");
        }

        if (this.timeSchedules != null) {
            for (TimeSchedule timeSchedule : this.timeSchedules) {
                timeSchedule.setStudygroupOneWay(null);
            }
        }

        this.timeSchedules = timeSchedules;
        for (TimeSchedule timeSchedule : this.timeSchedules) {
            timeSchedule.setStudygroupOneWay(this);
        }
    }

    /**
     * <h2>양방향 매핑을 위한 메서드</h2>
     * 기존 연결을 끊고, 새로 관계를 연결한다.<br>
     * <font color="white"><b>양방향 매핑 시 순환참조 가능성이 있으므로, 반대쪽에서 사용하면 절대 안됨</b></color="white"><br>
     * 참고: {@link Studygroup#setLeaderMemberOneWay(Member)}
     * @param leaderMember
     */
    public void setLeaderMember(Member leaderMember) {
        if (leaderMember == null) {
            throw new IllegalArgumentException("Leader Member cannot be null");
        }

        if(this.leaderMember != null) {
            this.leaderMember.getStudygroupsAsLeader().remove(this);
        }
        this.leaderMember = leaderMember;
        this.leaderMember.getStudygroupsAsLeader().add(this);
    }

    /**
     * <h2>양방향 매핑 중 순환 방지용으로 단방향으로만 추가하는 메서드</h2>
     * @param leaderMember 관계를 끊는 경우 null 가능
     */
    public void setLeaderMemberOneWay(Member leaderMember) {
        this.leaderMember = leaderMember;
    }
}
