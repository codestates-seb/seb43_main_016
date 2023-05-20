package com.codestates.edusync.model.studyaddons.searchtag.mapper;

import com.codestates.edusync.model.studyaddons.searchtag.entity.SearchTag;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.studyaddons.searchtag.dto.SearchTagDto;
import com.codestates.edusync.model.studyaddons.searchtag.dto.SearchTagResponseDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchTagMapper {

    default SearchTagResponseDto searchTagsToSearchTagResponseDto(List<SearchTag> tags) {
        SearchTagResponseDto result = new SearchTagResponseDto();
        HashMap<String, String> resultTags = new HashMap<>();

        tags.forEach(tag -> resultTags.put(tag.getTagKey(), tag.getTagValue()));
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
