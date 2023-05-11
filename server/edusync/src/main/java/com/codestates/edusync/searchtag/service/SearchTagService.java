package com.codestates.edusync.searchtag.service;

import com.codestates.edusync.searchtag.entity.SearchTag;
import com.codestates.edusync.searchtag.repository.SearchTagRepository;
import com.codestates.edusync.searchtag.utils.SearchTagManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchTagService implements SearchTagManager {
    private final SearchTagRepository searchTagRepository;

    @Override
    public List<SearchTag> getSearchTagList(String key) {
        return searchTagRepository.findByTagKey(key);
    }

    @Override
    public List<SearchTag> getAllSearchTagList() {
        return searchTagRepository.findAll();
    }

    @Override
    public void createSearchTags(List<SearchTag> tags) {
        searchTagRepository.saveAll(tags);
    }

    @Override
    public void deleteSearchTags(List<SearchTag> tags) {
        searchTagRepository.deleteAll(tags);
    }
}
