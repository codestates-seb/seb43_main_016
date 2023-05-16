package com.codestates.edusync.model.studyaddons.searchtag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class SearchTagDto {
    private Long studygroupId;
    @NotNull
    private HashMap<String, String> tags;
}
