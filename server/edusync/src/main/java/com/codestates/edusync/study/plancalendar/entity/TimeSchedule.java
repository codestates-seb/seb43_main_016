package com.codestates.edusync.study.plancalendar.entity;

import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.EAGER;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class TimeSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(length = 200)
    private String content;

    @Column
    private Timestamp startTime;
    @Column
    private Timestamp endTime;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "studygroup_id")
    private Studygroup studygroup;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "member_id")
    private Member member;


    /**
     * <h2>양방향 매핑을 위한 메서드</h2>
     * 기존 연결을 끊고, 새로 관계를 연결한다.<br>
     * <font color="white"><b>양방향 매핑 시 순환참조 가능성이 있으므로, 반대쪽에서 사용하면 절대 안됨</b></color="white"><br>
     * 참고: {@link TimeSchedule#setStudygroupOneWay(Studygroup)}
     * @param studygroup 양방향 매핑을 위한 객체
     */
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

    /**
     * <h2>양방향 매핑 중 순환 방지용으로 단방향으로만 추가하는 메서드</h2>
     * @param studygroup 관계를 끊는 경우 null 가능
     */
    public void setStudygroupOneWay(Studygroup studygroup) {
        this.studygroup = studygroup;
    }


    /**
     * <h2>양방향 매핑을 위한 메서드</h2>
     * 기존 연결을 끊고, 새로 관계를 연결한다.<br>
     * <font color="white"><b>양방향 매핑 시 순환참조 가능성이 있으므로, 반대쪽에서 사용하면 절대 안됨</b></color="white"><br>
     * 참고: {@link TimeSchedule#setMemberOneWay(Member)}
     * @param member 양방향 매핑을 위한 객체
     */
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

    /**
     * <h2>양방향 매핑 중 순환 방지용으로 단방향으로만 추가하는 메서드</h2>
     * @param member 관계를 끊는 경우 null 가능
     */
    public void setMemberOneWay(Member member) {
        this.member = member;
    }

}
