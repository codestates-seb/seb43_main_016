package com.codestates.edusync.model.study.studygroup.dto;

import com.codestates.edusync.model.common.dto.DateRangeDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class StudygroupResponseDto extends DateRangeDto.Response {
    private Long id;
    private String studyName;
    private String daysOfWeek;
    private Integer memberCountMin;
    private Integer memberCountMax;
    private Integer memberCountCurrent;
    private String platform;
    private String introduction;
    private Boolean requited;
    private HashMap<String, String> tags;
    private StudyLeader leader;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class StudyLeader {
        private Long id;
        private String nickName;
    }

    /**
     * 스터디 조회
     */
    @NoArgsConstructor
    @Getter
    @Setter
    public static class DtoList {
        private Long id;
        private String title;
        //private
    }
}
