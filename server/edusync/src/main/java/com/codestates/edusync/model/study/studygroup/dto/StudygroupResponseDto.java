package com.codestates.edusync.model.study.studygroup.dto;

import com.codestates.edusync.model.common.dto.DateRangeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class StudygroupResponseDto extends DateRangeDto.Response {
    private Long id;
    private String studyName;
    private List<String> daysOfWeek;
    private Integer memberCountMin;
    private Integer memberCountMax;
    private Integer memberCountCurrent;
    private String platform;
    private String introduction;
    private Boolean isRecruited;
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
        private List<String> tagValues;
    }

    /**
     * 스터디 모집 상태
     */
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Status {
        private Boolean status;
    }
}
