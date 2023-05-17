package com.codestates.edusync.model.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class TimeRangeDto {

    @NoArgsConstructor
    @Getter
    public static class Post {
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private Timestamp startTime;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private Timestamp endTime;
    }

    @NoArgsConstructor
    @Getter
    public static class Patch {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private Timestamp startTime;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private Timestamp endTime;
    }

    @NoArgsConstructor
    @Getter
    public static class Response {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private Timestamp startTime;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private Timestamp endTime;
    }
}
