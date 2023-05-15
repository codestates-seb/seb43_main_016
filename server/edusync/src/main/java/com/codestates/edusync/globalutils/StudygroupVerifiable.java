package com.codestates.edusync.globalutils;

import com.codestates.edusync.study.studygroup.entity.Studygroup;

public interface StudygroupVerifiable {
    /**
     * <h2>검색할 스터디 그룹에 대한 검증</h2>
     * 해당 스터디 그룹이 존재하는지 확인 후,<br>
     * 존재한다면 스터디 그룹을 리턴한다.<br>
     * <font color=white>404 Not Found </font> 스터디 그룹이 존재하지 않음<br>
     * @param studygroupId 스터디 그룹의 식별자
     * @return Studygroup
     */
    Studygroup findVerifiedStudygroup(Long studygroupId);
}
