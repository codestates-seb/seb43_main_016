package com.codestates.edusync.model.studyaddons.searchtag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class SearchTagDto {
    private Long studygroupId;
    @NotNull
    private Map<String, Set<String>> tags;
}
