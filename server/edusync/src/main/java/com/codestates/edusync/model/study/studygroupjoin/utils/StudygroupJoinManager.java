package com.codestates.edusync.model.study.studygroupjoin.utils;

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
    public StudygroupJoin findStudygroupJoinCandidate(Long studygroupId, String nickName);

    /**
     * 스터디 가입 멤버 조회
     * @param studygroupId
     * @param nickName
     * @return
     */
    public StudygroupJoin findStudygroupJoin(Long studygroupId, String nickName);

    /**
     * 스터디 가입 대기 리스트 조회
     * @param studygroupId
     * @return
     */
    public List<StudygroupJoin> findStudygroupJoinCandidateList(Long studygroupId, Member loginMember);

    /**
     * 스터디 멤버 리스트 조회
     * @param studygroupId
     * @return
     */
    public List<StudygroupJoin> findStudygroupJoinList(Long studygroupId);

    /**
     * 스터디 가입 신청
     * @param studygroupId
     */
    public void createStudygroupJoin(Long studygroupId, Member loginMember);

    /**
     * 스터디 가입 신청 철회
     * @param studygroupId
     */
    public void deleteStudygroupJoinCandidate(Long studygroupId, Member loginMember);

    /**
     * 스터디 탈퇴
     * @param studygourId
     */
    public void deleteStudygroupJoin(Long studygourId, Member loginMember);

    /**
     * 스터디 리더가 가입 승인
     * @param studygroupId
     * @param nickName
     */
    public void approveStudygroupJoin(Long studygroupId, String nickName, Member loginMember);

    /**
     * 스터디 리더가 가입 거절
     * @param studygroupId
     * @param nickName
     */
    public void rejectStudygroupJoinCandidate(Long studygroupId, String nickName, Member loginMember);

    /**
     * 스터디 리더가 멤버 강퇴
     * @param studygroupId
     * @param nickName
     */
    public void deleteStudygroupJoinKick(Long studygroupId, String nickName, Member loginMember);
}
