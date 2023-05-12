package com.codestates.edusync.study.postcomment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class StudygroupPostCommentDto {

    @NoArgsConstructor
    @Getter
    public static class Post {
        @NotNull
        private String content;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch {
        private Long id;

        private String content;
    }
}
