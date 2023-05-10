package com.codestates.edusync.member.entity;

import com.codestates.edusync.audit.Auditable;
import com.codestates.edusync.infodto.timeschedule.entity.TimeSchedule;
import com.codestates.edusync.study.postcomment.entity.StudygroupPostComment;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import com.codestates.edusync.study.studygroupjoin.entity.StudygroupJoin;
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
public class Member extends Auditable {
    @Id // 식별자 등록
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 식별자를 자동으로 생성
    private Long id;
    @Column(nullable = false, updatable = true, unique = true)
    private String nickName;
    @Column(nullable = false, updatable = false, unique = true)
    private String email;
    @Column(length = 2147483647)    // fixme : 길이 제한 걸릴 경우 length = -1 이나 columnDefinition = "TEXT" 타입 고려
    private String profileImage;
    private String password;
    @Column(length = 200)
    private String address;
    @Column(length = 50)
    private String grade;

    public Member(Long id, String nickName, String email, String profileImage) { // 테스트코드 작성용 생성자
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.profileImage = profileImage;
    }

    @ElementCollection(fetch = FetchType.EAGER) // 별도의 테이블로 생성해서 저장 // 권한 여러개 설정할거면 나중에 바꾸기 (String roles 지우고 관련 메서드 체크!)
    private List<String> roles = new ArrayList<>(); // 권한 테이블
    private String withMe;
    private String aboutMe;
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

    @OneToMany(mappedBy = "leaderMember", cascade = {PERSIST, MERGE}, fetch = LAZY)
    private List<Studygroup> studygroupsAsLeader = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    private List<StudygroupJoin> studygroupJoins = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    private List<TimeSchedule> timeSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    private List<StudygroupPostComment> studygroupPostComments = new ArrayList<>();

    /**
     * <h2>양방향 매핑을 위한 One To Many 쪽의 Setter 설정</h2>
     * 기존에 연결되어있던 관계를 끊고, 양쪽 객체를 새로 연결해준다.
     * @param studygroupsAsLeader  새로 매핑할 객체의 리스트( 시간표 )
     */
    public void setStudygroupsAsLeader(List<Studygroup> studygroupsAsLeader) {
        if (studygroupsAsLeader == null) {
            throw new IllegalArgumentException("studygroup As Leader List cannot be null");
        }

        if (this.studygroupsAsLeader != null) {
            for (Studygroup studygroup : this.studygroupsAsLeader) {
                studygroup.setLeaderMemberOneWay(null);
            }
        }

        this.studygroupsAsLeader = studygroupsAsLeader;
        for (Studygroup studygroup : this.studygroupsAsLeader) {
            studygroup.setLeaderMemberOneWay(this);
        }
    }

    /**
     * <h2>양방향 매핑을 위한 One To Many 쪽의 Setter 설정</h2>
     * 기존에 연결되어있던 관계를 끊고, 양쪽 객체를 새로 연결해준다.
     * @param studygroupJoins  새로 매핑할 객체의 리스트( 시간표 )
     */
    public void setStudygroupJoin(List<StudygroupJoin> studygroupJoins) {
        if (studygroupJoins == null) {
            throw new IllegalArgumentException("Studygroup Join List cannot be null");
        }

        if (this.studygroupJoins != null) {
            for (StudygroupJoin studygroupJoin : this.studygroupJoins) {
                studygroupJoin.setMemberOneWay(null);
            }
        }

        this.studygroupJoins = studygroupJoins;
        for (StudygroupJoin studygroupJoin : this.studygroupJoins) {
            studygroupJoin.setMemberOneWay(this);
        }
    }

    /**
     * <h2>양방향 매핑을 위한 One To Many 쪽의 Setter 설정</h2>
     * 기존에 연결되어있던 관계를 끊고, 양쪽 객체를 새로 연결해준다.
     * @param timeSchedules  새로 매핑할 객체의 리스트( 시간표 )
     */
    public void setTimeSchedule(List<TimeSchedule> timeSchedules) {
        if (timeSchedules == null) {
            throw new IllegalArgumentException("TimeSchedule List cannot be null");
        }

        if (this.timeSchedules != null) {
            for (TimeSchedule timeSchedule : this.timeSchedules) {
                timeSchedule.setMemberOneWay(null);
            }
        }

        this.timeSchedules = timeSchedules;
        for (TimeSchedule timeSchedule : this.timeSchedules) {
            timeSchedule.setMemberOneWay(this);
        }
    }

    /**
     * <h2>양방향 매핑을 위한 One To Many 쪽의 Setter 설정</h2>
     * 기존에 연결되어있던 관계를 끊고, 양쪽 객체를 새로 연결해준다.
     * @param studygroupPostComments  새로 매핑할 객체의 리스트( 시간표 )
     */
    public void setStudygroupPostComment(List<StudygroupPostComment> studygroupPostComments) {
        if (studygroupPostComments == null) {
            throw new IllegalArgumentException("StudygroupPostComment List cannot be null");
        }

        if (this.studygroupPostComments != null) {
            for (StudygroupPostComment studygroupPostComment : this.studygroupPostComments) {
                studygroupPostComment.setMemberOneWay(null);
            }
        }

        this.studygroupPostComments = studygroupPostComments;
        for (StudygroupPostComment studygroupPostComment : this.studygroupPostComments) {
            studygroupPostComment.setMemberOneWay(this);
        }
    }
}
