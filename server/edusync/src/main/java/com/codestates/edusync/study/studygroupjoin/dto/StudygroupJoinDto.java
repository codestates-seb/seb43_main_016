package com.codestates.edusync.study.studygroupjoin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class StudygroupJoinDto {
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Dto {
        @NotNull
        private String nickName;
    }
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {
        private List<String> nickName;
    }
}
