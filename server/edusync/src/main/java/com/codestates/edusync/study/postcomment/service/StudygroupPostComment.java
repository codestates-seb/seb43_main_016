package com.codestates.edusync.study.postcomment.service;

import com.codestates.edusync.study.studygroup.repository.StudygroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudygroupPostComment {
    private final StudygroupRepository studygroupRepository;


}
