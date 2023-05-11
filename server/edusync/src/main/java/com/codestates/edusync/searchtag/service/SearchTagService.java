package com.codestates.edusync.searchtag.service;

import com.codestates.edusync.searchtag.entity.SearchTag;
import com.codestates.edusync.searchtag.repository.SearchTagRepository;
import com.codestates.edusync.searchtag.utils.SearchTagManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class SearchTagService implements SearchTagManager {
    private final SearchTagRepository searchTagRepository;

    @Override
    public List<SearchTag> getSearchTagList(String key) {
        return searchTagRepository.findAllByTagKey(key);
    }

    @Override
    public List<SearchTag> getAllSearchTagList() {
        return searchTagRepository.findAll();
    }

    @Override
    public List<SearchTag> createSearchTags(List<SearchTag> tags) {
        return searchTagRepository.saveAll(tags);
    }

    @Override
    public void deleteSearchTags(List<SearchTag> tags) {
        searchTagRepository.deleteAll(tags);
    }
}
