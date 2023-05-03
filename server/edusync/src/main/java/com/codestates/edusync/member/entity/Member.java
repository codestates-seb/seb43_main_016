package com.codestates.edusync.member.entity;

import com.codestates.edusync.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Column(length = 2147483647)
    private String profileImage;
    private String password;

    public Member(Long id, String nickName, String email, String profileImage) { // 테스트코드 작성용 생성자
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.profileImage = profileImage;
    }

    @ElementCollection(fetch = FetchType.EAGER) // 별도의 테이블로 생성해서 저장 // 권한 여러개 설정할거면 나중에 바꾸기 (String roles 지우고 관련 메서드 체크!)
    private List<String> roles = new ArrayList<>(); // 권한 테이블

    private String location;

    private String title;

    private String aboutMe;

}
