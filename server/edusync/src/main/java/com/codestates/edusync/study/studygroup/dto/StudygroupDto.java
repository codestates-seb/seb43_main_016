package com.codestates.edusync.study.studygroup.dto;

import com.codestates.edusync.infodto.calendarinfo.dto.CalendarInfoDto;
import com.codestates.edusync.infodto.timeschedule.dto.TimeScheduleInfoDto;
import com.codestates.edusync.searchtag.entity.SearchTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public class StudygroupDto {

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post {
        @NotNull
        private String studyName;

        private CalendarInfoDto calendar;
        private List<TimeScheduleInfoDto> timeSchedules;

        @Positive
        private Integer maxClassmateCount;

        @NotNull
        private String platform;

        @NotNull
        private String introduction;

        private List<SearchTag> tags;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch {
        private String studyName;

        private CalendarInfoDto calendar;
        private List<TimeScheduleInfoDto> timeSchedules;

        @Positive
        private Integer maxClassmateCount;

        private String platform;
        private String introduction;

        private List<SearchTag> tags;
    }
}
