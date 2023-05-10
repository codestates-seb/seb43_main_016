package com.codestates.edusync.searchtag.service;

import com.codestates.edusync.searchtag.repository.SearchTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchTagService {
    private final SearchTagRepository searchTagRepository;

}
