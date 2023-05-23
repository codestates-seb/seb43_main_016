package com.codestates.edusync.model.study.studygroup.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CommonStudygroupDto {
    String getStudyName();
    List<String> getDaysOfWeek();
    Integer getMemberCountMin();
    Integer getMemberCountMax();
    String getPlatform();
    String getIntroduction();
    Map<String, Set<String>> getTags();
    String getColor();

    LocalDateTime getStudyPeriodStart();
    LocalDateTime getStudyPeriodEnd();
    LocalDateTime getStudyTimeStart();
    LocalDateTime getStudyTimeEnd();

}
