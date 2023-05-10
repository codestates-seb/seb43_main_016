package com.codestates.edusync.searchtag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SearchTagResponseDto {
    private HashMap<String, String> tags;
}
