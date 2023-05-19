package com.codestates.edusync.model.study.plancalendar.dto;

import com.codestates.edusync.model.common.dto.TimeRangeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public class CalendarMemberDto {

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post {
        @NotNull
        private TimeScheduleDto.Post timeSchedule;
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
        public static class Post extends TimeRangeDto.Post {
            @NotNull
            private String title;

            @Nullable
            private String platform;

            @Nullable
            private String description;

            @Nullable
            private String color;
        }

        @NoArgsConstructor
        @Getter
        public static class Patch extends TimeRangeDto.Patch {
            private Long id;
            private String title;
            private String platform;
            private String description;
            private String color;
        }
    }
}
