package com.codestates.edusync.model.studyaddons.searchtag.entity;

import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "studygroup_id")
    private Studygroup studygroup;


    /**
     * <h2>양방향 매핑을 위한 메서드</h2>
     * 기존 연결을 끊고, 새로 관계를 연결한다.<br>
     * <font color="white"><b>양방향 매핑 시 순환참조 가능성이 있으므로, 반대쪽에서 사용하면 절대 안됨</b></color="white"><br>
     * 참고: {@link SearchTag#setStudygroupOneWay(Studygroup)}
     * @param studygroup    양방향 매핑을 위한 객체
     */
    public void setStudygroup(Studygroup studygroup) {
        if (studygroup == null) {
            throw new IllegalArgumentException("Studygroup cannot be null");
        }

        if(this.studygroup != null) {
            this.studygroup.getSearchTags().remove(this);
        }
        this.studygroup = studygroup;
        this.studygroup.getSearchTags().add(this);
    }

    /**
     * <h2>양방향 매핑 중 순환 방지용으로 단방향으로만 추가하는 메서드</h2>
     * @param studygroup 관계를 끊는 경우 null 가능
     */
    public void setStudygroupOneWay(Studygroup studygroup) {
        this.studygroup = studygroup;
    }
}
