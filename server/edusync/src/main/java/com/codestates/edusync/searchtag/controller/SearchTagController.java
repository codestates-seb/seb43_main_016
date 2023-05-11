package com.codestates.edusync.searchtag.controller;

import com.codestates.edusync.searchtag.dto.SearchTagResponseDto;
import com.codestates.edusync.searchtag.entity.SearchTag;
import com.codestates.edusync.searchtag.mapper.SearchTagMapper;
import com.codestates.edusync.searchtag.service.SearchTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
public class SearchTagController {
    private final SearchTagService searchTagService;
    private final SearchTagMapper mapper;

    private static final String DEFAULT_SEARCH_TAG_URL = "/search";

    /**
     * key(카테고리) 와 value(태그) 의 쌍을 응답한다.
     * @return
     */
    @GetMapping(DEFAULT_SEARCH_TAG_URL + "/all")
    public ResponseEntity getSearchTags() {
        List<SearchTag> tags = searchTagService.getAllSearchTagList();

        return new ResponseEntity<>(
                mapper.searchTagsToSearchTagResponseDto(tags),
                HttpStatus.OK
        );
    }

    /**
     * key(카테고리)를 요청받아서 value(태그)들만 리턴한다.
     * @param key
     * @return
     */
    @GetMapping(DEFAULT_SEARCH_TAG_URL)
    public ResponseEntity getSearchTags(@RequestParam("key") String key) {
        List<SearchTag> tags = searchTagService.getSearchTagList(key);

        return new ResponseEntity<>(
                mapper.searchTagsToSearchTagResponseDto(tags),
                HttpStatus.OK
        );
    }
}
