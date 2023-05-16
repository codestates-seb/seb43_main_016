package com.codestates.edusync.model.common.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}