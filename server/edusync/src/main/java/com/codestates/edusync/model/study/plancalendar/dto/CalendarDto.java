package com.codestates.edusync.model.study.plancalendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

public class CalendarDto {

    @NoArgsConstructor
    @Getter
    public static class Post {
        @NotNull
        private List<TimeScheduleDto.Post> timeSchedules;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch {
        @NotNull
        private TimeScheduleDto.Patch timeSchedule;
    }

    public static class TimeScheduleDto {

        @NoArgsConstructor
        @Getter
        public static class Post {
            @NotNull
            private String title;

            @Nullable
            private String content;

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
            private Long id;
            private String title;
            private String content;

            @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
            private Timestamp startTime;

            @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
            private Timestamp endTime;
        }

    }
}
