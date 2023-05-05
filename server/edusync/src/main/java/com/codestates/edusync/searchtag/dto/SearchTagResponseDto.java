package com.codestates.edusync.searchtag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SearchTagResponseDto {
    private List<String> tags;  // todo: 입력받은 tagKey 에 해당하는 tagValue 매핑해야함
}
