package com.codestates.edusync.study.studygroup.dto;

import com.codestates.edusync.searchtag.entity.SearchTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public class StudygroupDto {

    @NoArgsConstructor
    @Getter
    public static class Post {
        @NotNull
        private String studyName;
        private String studyPeriod;
        private String studyTime;

        @Positive
        private Integer memberCountMin;
        @Positive
        private Integer memberCountMax;

        @NotNull
        private String platform;

        @NotNull
        private String introduction;

        private List<SearchTag> tags;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch {
        private Long id;
        private String studyName;
        private String studyPeriod;
        private String studyTime;

        @Positive
        private Integer memberCountMin;
        @Positive
        private Integer memberCountMax;

        private String platform;
        private String introduction;

        private List<SearchTag> tags;
    }
}
