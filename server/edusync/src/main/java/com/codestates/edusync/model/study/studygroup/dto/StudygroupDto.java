package com.codestates.edusync.model.study.studygroup.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class StudygroupDto {

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post {
        @NotNull
        private String studyName;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Timestamp studyPeriodStart;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Timestamp studyPeriodEnd;

        private List<Integer> daysOfWeek;

        @NotNull
        @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
        private Timestamp studyTimeStart;

        @NotNull
        @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
        private Timestamp studyTimeEnd;

        @Positive
        private Integer memberCountMin;

        @Positive
        private Integer memberCountMax;

        @Positive
        private Integer memberCountCurrent;

        @NotNull
        private String platform;

        @NotNull
        private String introduction;

        private HashMap<String, String> tags;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch {
        @NotNull
        private Long id;

        private String studyName;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private Timestamp studyPeriodStart;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private Timestamp studyPeriodEnd;

        private List<Integer> daysOfWeek;

        @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
        private Timestamp studyTimeStart;

        @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
        private Timestamp studyTimeEnd;

        @Positive
        private Integer memberCountMin;

        @Positive
        private Integer memberCountMax;

        @Positive
        private Integer memberCountCurrent;

        private String platform;

        private String introduction;

        private HashMap<String, String> tags;
    }
}