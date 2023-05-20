package com.codestates.edusync.model.studyaddons.searchtag.mapper;

import com.codestates.edusync.model.common.utils.TagFormatConverter;
import com.codestates.edusync.model.studyaddons.searchtag.entity.SearchTag;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.studyaddons.searchtag.dto.SearchTagDto;
import com.codestates.edusync.model.studyaddons.searchtag.dto.SearchTagResponseDto;
import org.mapstruct.Mapper;

import java.util.*;

@Mapper(componentModel = "spring")
public interface SearchTagMapper {
    default SearchTagResponseDto searchTagsToSearchTagResponseDto(List<SearchTag> tags) {
        SearchTagResponseDto result = new SearchTagResponseDto();
        result.setTags(TagFormatConverter.listToMap(tags));

        return result;
    }

    default List<SearchTag> searchTagDtoToSearchTags(SearchTagDto postDto) {
        Studygroup studygroup = new Studygroup();
        studygroup.setId(postDto.getStudygroupId());

        return TagFormatConverter.mapToList(postDto.getTags(), studygroup);
    }
}
