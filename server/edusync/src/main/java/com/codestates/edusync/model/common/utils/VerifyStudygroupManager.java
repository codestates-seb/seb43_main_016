package com.codestates.edusync.model.common.utils;

import com.codestates.edusync.model.study.studygroup.entity.Studygroup;

public interface VerifyStudygroupManager {
    /**
     * <h2>검색할 스터디 그룹에 대한 검증</h2>
     * 해당 스터디 그룹이 존재하는지 확인 후,<br>
     * 존재한다면 스터디 그룹을 리턴한다.<br>
     * <font color=white>404 Not Found </font> 스터디 그룹이 존재하지 않음<br>
     * @param studygroupId 스터디 그룹의 식별자
     * @return Studygroup
     */
    Studygroup findStudygroup(Long studygroupId);


    /**
     * <h2>스터디 그룹에 대한 리더라면 true</h2>
     * 해당 스터디 그룹에 대한 리더인지 확인 한다<br>
     * @param email
     * @param studygroupId
     * @return true, false 값으로 리턴한다.<br>
     */
    boolean verifyMemberLeaderOfStudygroup(String email, Long studygroupId);
}
