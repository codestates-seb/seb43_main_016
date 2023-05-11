package com.codestates.edusync.searchtag.dto;

import com.codestates.edusync.searchtag.entity.SearchTag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
public class SearchTagDto {
    @NotNull
    private List<SearchTag> tags;
}
