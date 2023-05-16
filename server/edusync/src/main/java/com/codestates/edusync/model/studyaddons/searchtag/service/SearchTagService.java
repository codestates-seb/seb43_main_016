package com.codestates.edusync.model.studyaddons.searchtag.service;

import com.codestates.edusync.model.studyaddons.searchtag.entity.SearchTag;
import com.codestates.edusync.model.studyaddons.searchtag.repository.SearchTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class SearchTagService implements SearchTagManager {
    private final SearchTagRepository searchTagRepository;

    public List<SearchTag> getList(Long studygroupId) { return searchTagRepository.findAllByStudygroupId(studygroupId); }

    @Override
    public List<SearchTag> getList(String key) {
        return searchTagRepository.findAllByTagKey(key);
    }

    @Override
    public List<SearchTag> getAllList() {
        return searchTagRepository.findAll();
    }

    @Override
    public List<SearchTag> createList(List<SearchTag> tags) {

        return searchTagRepository.saveAll(tags);
    }

    @Override
    public void deleteList(List<SearchTag> tags) {
        tags.forEach(tag -> searchTagRepository.deleteByTagKeyAndTagValue(tag.getTagKey(), tag.getTagValue()));
    }

    @Override
    public void deleteList(Long studygroupId) {
        searchTagRepository.deleteAllByStudygroupId(studygroupId);
    }

    @Override
    public void updateList(Long studygroupId, List<SearchTag> searchTags) {
        searchTagRepository.deleteAllByStudygroupId(studygroupId);

        createList(searchTags);
    }
}
