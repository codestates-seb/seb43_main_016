package com.codestates.edusync.study.plancalendar.classmate.dto;

import com.codestates.edusync.study.plancalendar.entity.TimeSchedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CalendarClassmateDto {

    @NoArgsConstructor
    @Getter
    public static class Post {
        @NotNull
        private List<TimeSchedule> timeSchedules;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch {
        @NotNull
        private List<TimeSchedule> timeSchedules;
    }
}
