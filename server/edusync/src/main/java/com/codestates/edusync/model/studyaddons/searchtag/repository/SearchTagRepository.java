package com.codestates.edusync.model.studyaddons.searchtag.repository;

import com.codestates.edusync.model.studyaddons.searchtag.entity.SearchTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchTagRepository extends JpaRepository<SearchTag, Long> {

    List<SearchTag> findAllByTagKey(String key);
    List<SearchTag> findAllByStudygroupId(Long studygroupId);

    void deleteAllByStudygroupId(Long studygroupId);

    void deleteByTagKeyAndTagValue(String tagKey, String tagValue);
}
