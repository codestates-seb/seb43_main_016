package com.codestates.edusync.member.entity;

import com.codestates.edusync.localmember.entity.LocalMember;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 데이터베이스에 위임 (기본키 자동 생성)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private LocalMember localMember;

}
