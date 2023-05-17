package com.codestates.edusync.model.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TimeRangeDto {

    @NoArgsConstructor
    @Getter
    public static class Post {
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime studyTimeStart;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime studyTimeEnd;
    }

    @NoArgsConstructor
    @Getter
    public static class Patch {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime studyTimeStart;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime studyTimeEnd;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime studyTimeStart;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime studyTimeEnd;
    }
}
