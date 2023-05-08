package com.codestates.edusync.study.classmate.utils;

import com.codestates.edusync.study.studygroup.entity.Studygroup;

public interface ClassmateManager {
    /**
     * <h2>스터디에 가입 신청을 한다</h2>
     * <font color="white"><b> 403 forbidden </b></font> 로그인을 하지 않은 경우, 권한이 없음<br>
     * <font color="white"><b> 404 not found </b></font> 스터디를 찾을 수 없음<br>
     * <font color="white"><b> 409 already exist in this studygroup </b></font> 이미 스터디그룹에 가입되어있는 경우<br>
     * @param studygroupId
     * @param classmateId
     * @return
     */
    Studygroup signUpToStudygroup(Long studygroupId, Long classmateId);


    /**
     * <h2>스터디장이 가입 신청을 승인한다</h2>
     * <font color="white"><b> 403 forbidden </b></font>
     * @param studygroupId
     * @param classmateId
     * @return
     */
    Studygroup approveSignUpFromStudyLeader(Long studygroupId, Long classmateId);

    /**
     * <h2>스터디장이 가입 신청을 거절한다</h2>
     * <font color="white"><b> 403 forbidden </b></font> 가입신청 거절을 위한 권한이 없음<br>
     *
     * @param studygroupId
     * @param studygroupJoinId
     */
    void rejectByStudyLeader(Long studygroupId, Long studygroupJoinId);

    /**
     * <h2>스터디에 가입을 신청한 스터디원이 가입신청을 철회한다.</h2>
     * @param studygroupJoinId
     */
    void cancelSubscription(Long studygroupJoinId);

    /**
     * 스터디 그룹이 존재하는지 확인
     * @param studygroupId
     */
    void verifyStudygroup(Long studygroupId);

    /**
     * 이미 동일 스터디에 동일 클래스메이트가 신청을 한 경우의 예외처리
     * @param studygroupId
     * @param classmateId
     */
    void verifyExistCandidate(Long studygroupId, Long classmateId);
}
