package com.codestates.edusync.model.study.plancalendar.dto;

import com.codestates.edusync.model.common.dto.TimeRangeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public class CalendarDto {

    @NoArgsConstructor
    @Getter
    public static class List {
        @NotNull
        private java.util.List<TimeScheduleDto.Post> timeSchedules;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Single {
        @NotNull
        private TimeScheduleDto.Patch timeSchedule;
    }

    public static class TimeScheduleDto {

        @NoArgsConstructor
        @Getter
        public static class Post extends TimeRangeDto.Post {
            @NotNull
            private String title;

            @Nullable
            private String content;
        }

        @NoArgsConstructor
        @Getter
        public static class Patch extends TimeRangeDto.Patch {
            private Long id;
            private String title;
            private String content;
        }

    }
}
