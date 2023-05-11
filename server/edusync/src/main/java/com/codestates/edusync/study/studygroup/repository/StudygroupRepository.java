package com.codestates.edusync.study.studygroup.repository;

import com.codestates.edusync.study.studygroup.entity.Studygroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StudygroupRepository extends JpaRepository<Studygroup, Long> {

}
