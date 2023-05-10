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
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class StudygroupResponseDto {
    private Long id;
    private String studyName;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Timestamp studyPeriodStart;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Timestamp studyPeriodEnd;
    private String daysOfWeek;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private Timestamp studyTimeStart;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private Timestamp studyTimeEnd;
    private Integer memberCountMin;
    private Integer memberCountMax;
    private String platform;
    private String introduction;
    private Boolean requited;
    private List<SearchTag> tags;
    private StudyLeader leader;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class StudyLeader {
        private Long id;
        private String nickName;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class DtoList {
        private Long id;
        private String title;
        private List<SearchTag> tags;
    }
//    private Count count;
//
//    @NoArgsConstructor
//    @Getter
//    @Setter
//    public static class Count {
//        private Integer waiter;
//        private Integer classmate;
//        private Integer comment;
//        private Integer tag;
//    }
}
