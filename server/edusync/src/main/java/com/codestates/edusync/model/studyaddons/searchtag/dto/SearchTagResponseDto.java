package com.codestates.edusync.model.studyaddons.searchtag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class SearchTagResponseDto {
    private Map<String, Set<String>> tags;
}
