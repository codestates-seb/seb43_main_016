package com.codestates.edusync.model.study.studygroupjoin.entity;

import com.codestates.edusync.model.common.entity.Auditable;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "studygroup_join")
public class StudygroupJoin extends Auditable {
    @Column(name = "is_approved")
    private Boolean isApproved = false;

    @ManyToOne(fetch = EAGER, cascade = {MERGE})
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = EAGER, cascade = {MERGE})
    @JoinColumn(name = "studygroup_id")
    private Studygroup studygroup;


    /**
     * <h2>양방향 매핑을 위한 메서드</h2>
     * 기존 연결을 끊고, 새로 관계를 연결한다.<br>
     * <font color="white"><b>양방향 매핑 시 순환참조 가능성이 있으므로, 반대쪽에서 사용하면 절대 안됨</b></color="white"><br>
     * 참고: {@link StudygroupJoin#setStudygroupOneWay(Studygroup)}
     * @param studygroup 양방향 매핑을 위한 인자
     */
    public void setStudygroup(Studygroup studygroup) {
        if (studygroup == null) {
            throw new IllegalArgumentException("Studygroup cannot be null");
        }

        if(this.studygroup != null) {
            this.studygroup.getStudygroupJoins().remove(this);
        }
        this.studygroup = studygroup;
        this.studygroup.getStudygroupJoins().add(this);
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
     * 참고: {@link StudygroupJoin#setStudygroupOneWay(Studygroup)}
     * @param member 양방향 매핑을 위한 인자
     */
    public void setMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        if(this.member != null) {
            this.member.getStudygroupJoins().remove(this);
        }
        this.member = member;
        this.member.getStudygroupJoins().add(this);
    }

    /**
     * <h2>양방향 매핑 중 순환 방지용으로 단방향으로만 추가하는 메서드</h2>
     * @param member 관계를 끊는 경우 null 가능
     */
    public void setMemberOneWay(Member member) {
        this.member = member;
    }
}
