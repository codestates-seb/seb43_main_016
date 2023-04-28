package com.codestates.edusync.localmember.entity;

import com.codestates.edusync.audit.Auditable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class LocalMember extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;
    @Column(nullable = false, updatable = true, unique = true)
    private String password;
    @Column(nullable = false, updatable = true, unique = true)
    private String nickName;
    @Column(length = 2147483647)
    private String profileImage;

}
