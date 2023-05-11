package com.codestates.edusync.searchtag.controller;

import com.codestates.edusync.searchtag.dto.SearchTagDto;
import com.codestates.edusync.searchtag.dto.SearchTagResponseDto;
import com.codestates.edusync.searchtag.entity.SearchTag;
import com.codestates.edusync.searchtag.mapper.SearchTagMapper;
import com.codestates.edusync.searchtag.service.SearchTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
public class SearchTagController {
    private final SearchTagService searchTagService;
    private final SearchTagMapper mapper;

    private static final String DEFAULT_SEARCH_TAG_URL = "/search";
    private static final String DEFAULT_STUDYGROUP_URL = "/studygroup";


    @PostMapping(DEFAULT_SEARCH_TAG_URL + DEFAULT_STUDYGROUP_URL + "/{studygroup-id}")
    public ResponseEntity postSearchTags(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                          @Valid @RequestBody SearchTagDto dto) {
        dto.setStudygroupId(studygroupId);
        searchTagService.createSearchTags(mapper.searchTagDtoToSearchTags(dto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(DEFAULT_SEARCH_TAG_URL + DEFAULT_STUDYGROUP_URL + "/{studygroup-id}")
    public ResponseEntity patchSearchTags(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                          @Valid @RequestBody SearchTagDto dto) {
        dto.setStudygroupId(studygroupId);
        searchTagService.updateSearchTags(studygroupId, mapper.searchTagDtoToSearchTags(dto));

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(DEFAULT_SEARCH_TAG_URL + DEFAULT_STUDYGROUP_URL + "/{studygroup-id}")
    public ResponseEntity deleteSearchTagsWithStudygroupId(@PathVariable("studygroup-id") @Positive Long studygroupId) {
        searchTagService.deleteSearchTags(studygroupId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(DEFAULT_SEARCH_TAG_URL)
    public ResponseEntity deleteSearchTagsWithSearchTagList(@Valid @RequestBody SearchTagDto dto) {
        searchTagService.deleteSearchTags(mapper.searchTagDtoToSearchTags(dto));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * key(카테고리) 와 value(태그) 의 쌍을 응답한다.
     * @return
     */
    @GetMapping(DEFAULT_SEARCH_TAG_URL + "/all")
    public ResponseEntity getAllSearchTags() {
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
    public ResponseEntity getSearchTagsWithKey(@RequestParam("key") String key) {
        List<SearchTag> tags = searchTagService.getSearchTagList(key);

        return new ResponseEntity<>(
                mapper.searchTagsToSearchTagResponseDto(tags),
                HttpStatus.OK
        );
    }

    /**
     * 스터디 그룹 식별자를 요청받아서 테그 셋을 리턴한다.
     * @param
     * @return
     */
    @GetMapping(DEFAULT_SEARCH_TAG_URL + DEFAULT_STUDYGROUP_URL + "/{studygroup-id}")
    public ResponseEntity getSearchTagsWithStudygroupId(@PathVariable("studygroup-id") Long studygroupId) {
        List<SearchTag> tags = searchTagService.getSearchTagList(studygroupId);

        return new ResponseEntity<>(
                mapper.searchTagsToSearchTagResponseDto(tags),
                HttpStatus.OK
        );
    }
}
