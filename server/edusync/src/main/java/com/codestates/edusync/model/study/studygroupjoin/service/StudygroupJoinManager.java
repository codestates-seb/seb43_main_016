package com.codestates.edusync.model.study.studygroupjoin.service;

import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.studygroupjoin.entity.StudygroupJoin;

import java.util.List;

public interface StudygroupJoinManager {

    /**
     * 스터디 가입 요청 조회
     * @param studygroupId
     * @param nickName
     * @return
     */
    StudygroupJoin getCandidateByNickName(Long studygroupId, String nickName);

    /**
     * 스터디 가입 멤버 조회
     * @param studygroupId
     * @param nickName
     * @return
     */
    StudygroupJoin getMemberByNickName(Long studygroupId, String nickName);

    /**
     * 스터디 가입 대기 리스트 조회
     * @param studygroupId
     * @return
     */
    List<StudygroupJoin> getAllCandidateList(Long studygroupId, String email, boolean isLeader);

    /**
     * 스터디 멤버 리스트 조회
     * @param studygroupId
     * @return
     */
    List<StudygroupJoin> getAllMemberList(Long studygroupId);

    /**
     * 스터디 가입 신청
     * @param studygroupId
     */
    void createCandidate(Long studygroupId, String email);

    /**
     * 스터디 가입 신청 철회
     * @param studygroupId
     */
    void deleteCandidateSelf(Long studygroupId, String email);

    /**
     * 스터디 탈퇴
     * @param studygroupId
     */
    void deleteMemberSelf(Long studygroupId, String email);

    /**
     * 스터디 리더가 가입 승인
     * @param studygroupId
     * @param nickName
     */
    void approveCandidateByNickName(Long studygroupId, String nickName, String email);

    /**
     * 스터디 리더가 가입 거절
     * @param studygroupId
     * @param nickName
     */
    void rejectCandidateByNickName(Long studygroupId, String nickName, String email);

    /**
     * 스터디 리더가 멤버 강퇴
     * @param studygroupId
     * @param nickName
     */
    void kickOutMemberByNickName(Long studygroupId, String nickName, String email);

    /**
     * 사용자가 신청한 | 가입된 스터디 리스트 조회
     * @param email
     * @param isApproved
     * @return
     */
    List<Studygroup> getMyStudygroupList(String email, boolean isApproved);

    /**
     * 스터디 멤버 수
     * @param studygroupId
     * @return
     */
    int getStudygroupMemberCount(Long studygroupId);
}
