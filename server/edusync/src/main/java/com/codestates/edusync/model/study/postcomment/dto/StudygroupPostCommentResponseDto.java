package com.codestates.edusync.model.study.postcomment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StudygroupPostCommentResponseDto {
    private Long commentId;
    private Long studygroupId;
    private Long memberId;
    private String content;
}
