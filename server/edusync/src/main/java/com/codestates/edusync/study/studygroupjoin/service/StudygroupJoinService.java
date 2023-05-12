package com.codestates.edusync.study.studygroupjoin.service;

import com.codestates.edusync.study.studygroupjoin.repository.StudygroupJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudygroupJoinService {
    private final StudygroupJoinRepository studygroupJoinRepository;

}
