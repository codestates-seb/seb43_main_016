package com.codestates.edusync.study.classmate.repository;

import com.codestates.edusync.study.classmate.entity.Classmate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassmateRepository extends JpaRepository<Classmate, Long> {

}
