package com.codestates.edusync.study.studygroup.dto;

import com.codestates.edusync.study.classmate.dto.ClassmateResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StudygroupResponseDto {
    private Long id;
    private String studyName;
    private String calendar;
    private String timeSchedule;
    private String introduction;
    private Integer maxClassmateCount;
    private String platform;
    private String address;

    private ClassmateResponseDto leader;
    private Count count;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Count {
        private Integer waiter;
        private Integer classmate;
        private Integer comment;
        private Integer tag;
    }
}
