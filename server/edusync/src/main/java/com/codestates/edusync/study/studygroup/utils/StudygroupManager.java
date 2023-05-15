package com.codestates.edusync.study.studygroup.utils;

import com.codestates.edusync.study.studygroup.entity.Studygroup;
import org.springframework.data.domain.Page;

public interface StudygroupManager {

    /**
     * 스터디 등록
     * @param studygroup
     * @return
     */
    public Studygroup createStudygruop(Studygroup studygroup);

    /**
     * 스터디 정보 수정
     * @param studygroup
     * @return
     */
    public Studygroup updateStudygroup(String email, Studygroup studygroup);

    /**
     * 스터디 모집 상태 수정
     * @param studygroupId
     */
    public void updateStatusStudygroup(String email, Long studygroupId);

    /**
     * 스터디 조회
     * @param studygroupId
     * @return
     */
    public Studygroup findStudygroup(Long studygroupId);

    /**
     * 스터디 리스트 조회
     * @param page
     * @param size
     * @return
     */
    public Page<Studygroup> findStudygroups(Integer page, Integer size);

    /**
     * 스터디 삭제
     * @param studygroupId
     */
    public void deleteStudygroup(String email, Long studygroupId);
}
