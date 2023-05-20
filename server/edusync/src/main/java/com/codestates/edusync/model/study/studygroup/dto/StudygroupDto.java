package com.codestates.edusync.model.study.studygroup.dto;

import com.codestates.edusync.model.common.dto.DateRangeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudygroupDto {

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post extends DateRangeDto.Post {
        @NotNull
        private String studyName;

        private List<String> daysOfWeek;

        @Positive
        private Integer memberCountMin;

        @Positive
        private Integer memberCountMax;

        @NotNull
        private String platform;

        @NotNull
        private String introduction;

        private Map<String, Set<String>> tags;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch extends DateRangeDto.Patch {
        private String studyName;

        private List<String> daysOfWeek;

        @Positive
        private Integer memberCountMin;

        @Positive
        private Integer memberCountMax;


        private String platform;

        private String introduction;

        private Map<String, Set<String>> tags;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class PatchLeader {
        @NotNull
        private String nickName;
    }
}
