package com.codestates.edusync.study.studygroup.dto;

import com.codestates.edusync.searchtag.entity.SearchTag;
import com.codestates.edusync.study.studygroupjoin.dto.ClassmateResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Time;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class StudygroupResponseDto {
    private Long id;
    private String studyName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp studyPeriodStart;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp studyPeriodEnd;
    private String daysOfWeek;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private Timestamp studyTimeStart;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private Timestamp studyTimeEnd;
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
