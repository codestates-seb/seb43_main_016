package com.codestates.edusync.model.member.entity;

import com.codestates.edusync.model.common.entity.Auditable;
import com.codestates.edusync.model.study.postcomment.entity.StudygroupPostComment;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.studygroupjoin.entity.StudygroupJoin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member")
public class Member extends Auditable {
    @Id // 식별자 등록
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid = UUID.randomUUID().toString();
    @Column(nullable = false, updatable = true, unique = true)
    private String nickName;
    @Column(nullable = false, updatable = true, unique = true)
    private String email;
    @Column(length = -1)
    private String profileImage;
    private String password;
    @Column(length = 50)
    private String grade;

    public Member(Long id, String nickName, String email, String profileImage) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.profileImage = profileImage;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
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

    private Provider provider = Provider.LOCAL;

    public enum Provider {
        LOCAL("기본 회원"),
        GOOGLE("구글 연동 회원"),
        KAKAO("카카오 연동 회원"),
        NAVER("네이버 연동 회원");

        @Getter
        private String provider;

        Provider(String provider) {
            this.provider = provider;
        }
    }

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
