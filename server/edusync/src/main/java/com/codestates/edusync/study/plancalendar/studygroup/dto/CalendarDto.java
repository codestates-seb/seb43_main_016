package com.codestates.edusync.study.plancalendar.studygroup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        @Setter
        public static class Post {
            @NotNull
            private String title;

            @NotNull
            private String content;

            @NotNull
            private Timestamp start;

            @NotNull
            private Timestamp end;
        }

        @NoArgsConstructor
        @Getter
        @Setter
        public static class Patch {
            private Long id;
            private String title;
            private String content;
            private Timestamp start;
            private Timestamp end;
        }

    }
}
