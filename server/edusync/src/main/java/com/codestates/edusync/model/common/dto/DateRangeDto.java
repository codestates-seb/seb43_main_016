package com.codestates.edusync.model.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class DateRangeDto {

    @NoArgsConstructor
    @Getter
    public static class Post extends TimeRangeDto.Post {
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime studyPeriodStart;

        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime studyPeriodEnd;
    }

    @NoArgsConstructor
    @Getter
    public static class Patch extends TimeRangeDto.Patch {
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime studyPeriodStart;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime studyPeriodEnd;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response extends TimeRangeDto.Response {
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime studyPeriodStart;

        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime studyPeriodEnd;
    }
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OnlyPeriodResponse {
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime studyPeriodStart;

        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime studyPeriodEnd;
    }
}
