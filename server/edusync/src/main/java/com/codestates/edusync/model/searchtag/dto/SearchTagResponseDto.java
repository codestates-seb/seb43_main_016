package com.codestates.edusync.model.searchtag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class SearchTagResponseDto {
    private HashMap<String, String> tags;
}
