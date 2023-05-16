package com.codestates.edusync.model.studyaddons.searchtag.service;

import com.codestates.edusync.model.studyaddons.searchtag.entity.SearchTag;
import com.codestates.edusync.model.studyaddons.searchtag.repository.SearchTagRepository;
import com.codestates.edusync.model.studyaddons.searchtag.utils.SearchTagManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class SearchTagService implements SearchTagManager {
    private final SearchTagRepository searchTagRepository;

    public List<SearchTag> getSearchTagList(Long studygroupId) { return searchTagRepository.findAllByStudygroupId(studygroupId); }

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
        tags.forEach(tag -> searchTagRepository.deleteByTagKeyAndTagValue(tag.getTagKey(), tag.getTagValue()));
    }

    @Override
    public void deleteSearchTags(Long studygroupId) {
        searchTagRepository.deleteAllByStudygroupId(studygroupId);
    }

    @Override
    public void updateSearchTags(Long studygroupId, List<SearchTag> searchTags) {
        searchTagRepository.deleteAllByStudygroupId(studygroupId);

        createSearchTags(searchTags);
    }
}
