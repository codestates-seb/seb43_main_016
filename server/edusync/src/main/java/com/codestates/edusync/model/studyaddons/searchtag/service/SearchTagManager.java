package com.codestates.edusync.model.studyaddons.searchtag.service;

import com.codestates.edusync.model.studyaddons.searchtag.entity.SearchTag;

import java.util.List;

public interface SearchTagManager {
    /**
     * <h2>key(카테고리)를 받아 해당 key 에 대한 value(태그)의 리스트를 반환해준다</h2>
     * <s><font color="white"><b> 403 forbidden </b></font> 로그인을 하지 않은 경우</s><br>
     * <font color="white"><b> 404 not found </b></font> 해당 key 에 대한 값이 하나도 존재하지 않을 경우<br>
     * @param studygroupId   검색에 사용할 스터디 그룹의 식별자
     * @return
     */
    List<SearchTag> getList(Long studygroupId);

    /**
     * <h2>key(카테고리)를 받아 해당 key 에 대한 value(태그)의 리스트를 반환해준다</h2>
     * <s><font color="white"><b> 403 forbidden </b></font> 로그인을 하지 않은 경우</s><br>
     * <font color="white"><b> 404 not found </b></font> 해당 key 에 대한 값이 하나도 존재하지 않을 경우<br>
     * @param key   검색에 사용할 key(카테고리)
     * @return      key(카테고리)에 해당하는 value(태그)의 목록 전부
     */
    List<SearchTag> getList(String key);

    /**
     * <h2>모든 key(카테고리)와 value(태그)의 쌍을 리스트로 반환해준다</h2>
     * <s><font color="white"><b> 403 forbidden </b></font> 로그인을 하지 않은 경우</s><br>
     * <font color="white"><b> 404 not found </b></font> 해당 key 에 대한 값이 하나도 존재하지 않을 경우<br>
     * @return      key(카테고리)에 해당하는 value(태그)의 목록 전부
     */
    List<SearchTag> getAllList();

    /**
     * <h2>스터디 그룹에 태그를 생성한다</h2>
     * 스터디 그룹의 식별자와 searchTag 의 목록을 입력받아, DB 에 생성한다
     * @param tags  key(카테고리), value(태그) 의 목록
     */
    List<SearchTag> createByList(List<SearchTag> tags);

    /**
     * <h2>searchTag 의 목록을 입력받아, DB 에서 삭제한다</h2>
     * <font color="white"><b> 403 forbidden </b></font> 로그인을 하지 않은 경우<br>
     * <font color="white"><b> 403 forbidden </b></font> 해당 스터디의 리더가 아닌 경우<br>
     * <font color="white"><b> 404 not found </b></font> 해당 key 와 value 로 이루어진 목록이 하나도 존재하지 않는 경우<br>
     * @param tags
     */
    void deleteByList(List<SearchTag> tags);

    /**
     * <h2>그룹의 식별자를 입력받아, DB 에서 삭제한다</h2>
     * <font color="white"><b> 403 forbidden </b></font> 로그인을 하지 않은 경우<br>
     * <font color="white"><b> 403 forbidden </b></font> 해당 스터디의 리더가 아닌 경우<br>
     * <font color="white"><b> 404 not found </b></font> 스터디 그룹이 존재하지 않는 경우<br>
     * @param studygroupId
     */
    void deleteByList(Long studygroupId);

    void updateList(Long studygroupId, List<SearchTag> searchTags);
}
