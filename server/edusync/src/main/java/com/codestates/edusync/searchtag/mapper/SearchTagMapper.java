package com.codestates.edusync.searchtag.mapper;

import com.codestates.edusync.searchtag.dto.SearchTagDto;
import com.codestates.edusync.searchtag.dto.SearchTagResponseDto;
import com.codestates.edusync.searchtag.entity.SearchTag;
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

        return new ArrayList<>(postDto.getTags());
    }
}
