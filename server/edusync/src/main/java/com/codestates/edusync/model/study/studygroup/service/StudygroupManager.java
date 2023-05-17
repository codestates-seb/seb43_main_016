package com.codestates.edusync.model.study.studygroup.service;

import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import org.springframework.data.domain.Page;

public interface StudygroupManager {

    /**
     * 스터디 등록
     * @param studygroup
     * @return
     */
    Studygroup create(Studygroup studygroup);

    /**
     * 스터디 정보 수정
     * @param studygroup
     * @return
     */
    Studygroup update(String email, Studygroup studygroup);

    /**
     * 스터디 모집 상태 수정
     * @param studygroupId
     */
    boolean updateStatus(String email, Long studygroupId);

    /**
     * 스터디 조회
     * @param studygroupId
     * @return
     */
    Studygroup get(Long studygroupId);

    /**
     * 스터디 리스트 조회
     * @param page
     * @param size
     * @return
     */
    Page<Studygroup> getWithPaging(Integer page, Integer size);

    /**
     * 스터디 삭제
     * @param studygroupId
     */
    void delete(String email, Long studygroupId);
}
