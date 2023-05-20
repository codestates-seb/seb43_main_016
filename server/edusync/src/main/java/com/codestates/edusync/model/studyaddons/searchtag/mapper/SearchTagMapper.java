package com.codestates.edusync.model.studyaddons.searchtag.mapper;

import com.codestates.edusync.model.studyaddons.searchtag.entity.SearchTag;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.studyaddons.searchtag.dto.SearchTagDto;
import com.codestates.edusync.model.studyaddons.searchtag.dto.SearchTagResponseDto;
import org.mapstruct.Mapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Mapper(componentModel = "spring")
public interface SearchTagMapper {

    default SearchTagResponseDto searchTagsToSearchTagResponseDto(List<SearchTag> tags) {
        SearchTagResponseDto result = new SearchTagResponseDto();
        Map<String, Set<String>> resultTags = new HashMap<>();

        for( SearchTag tag : tags ) {
            String key = tag.getTagKey();
            String value = tag.getTagValue();
            Set<String> resultValues = new HashSet<>();
            if(resultTags.get(key) != null) {
                resultValues.addAll(resultTags.get(key));
            }
            resultValues.add(value);
            resultTags.put(key, resultValues);
        }
        result.setTags(resultTags);

        return result;
    }

    default List<SearchTag> searchTagDtoToSearchTags(SearchTagDto postDto) {
        Studygroup studygroup = new Studygroup();
        studygroup.setId(postDto.getStudygroupId());

        List<SearchTag> result = new ArrayList<>();
        postDto.getTags()
                .forEach((key, value) -> {
                    SearchTag resultTag = new SearchTag();
                    resultTag.setTagKey(key);
                    resultTag.setTagValue(value);
                    if( studygroup.getId() != null )
                        resultTag.setStudygroup(studygroup);

                    result.add(resultTag);
                });

        return result;
    }
}
