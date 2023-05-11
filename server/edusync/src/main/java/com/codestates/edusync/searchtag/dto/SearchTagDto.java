package com.codestates.edusync.searchtag.dto;

import com.codestates.edusync.searchtag.entity.SearchTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SearchTagDto {
    private Long studygroupId;
    @NotNull
    private HashMap<String, String> tags;
}
