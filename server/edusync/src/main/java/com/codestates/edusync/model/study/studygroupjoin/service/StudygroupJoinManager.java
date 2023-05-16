package com.codestates.edusync.model.study.studygroupjoin.service;

import com.codestates.edusync.model.member.entity.Member;
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
    StudygroupJoin getApprovedMemberByNickName(Long studygroupId, String nickName);

    /**
     * 스터디 가입 대기 리스트 조회
     * @param studygroupId
     * @return
     */
    List<StudygroupJoin> getCandidateList(Long studygroupId, Member loginMember);

    /**
     * 스터디 멤버 리스트 조회
     * @param studygroupId
     * @return
     */
    List<StudygroupJoin> getApprovedMemberList(Long studygroupId);

    /**
     * 스터디 가입 신청
     * @param studygroupId
     */
    void createCandidate(Long studygroupId, Member loginMember);

    /**
     * 스터디 가입 신청 철회
     * @param studygroupId
     */
    void deleteCandidateSelf(Long studygroupId, Member loginMember);

    /**
     * 스터디 탈퇴
     * @param studygroupId
     */
    void deleteApprovedMemberSelf(Long studygroupId, Member loginMember);

    /**
     * 스터디 리더가 가입 승인
     * @param studygroupId
     * @param nickName
     */
    void approveCandidateByNickName(Long studygroupId, String nickName, Member loginMember);

    /**
     * 스터디 리더가 가입 거절
     * @param studygroupId
     * @param nickName
     */
    void rejectCandidateByNickName(Long studygroupId, String nickName, Member loginMember);

    /**
     * 스터디 리더가 멤버 강퇴
     * @param studygroupId
     * @param nickName
     */
    void kickOutApprovedMemberByNickName(Long studygroupId, String nickName, Member loginMember);
}
