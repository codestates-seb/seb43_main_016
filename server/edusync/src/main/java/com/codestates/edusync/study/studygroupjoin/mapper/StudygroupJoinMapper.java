package com.codestates.edusync.study.studygroupjoin.mapper;

import com.codestates.edusync.study.studygroupjoin.dto.StudygroupJoinDto;
import com.codestates.edusync.study.studygroupjoin.entity.StudygroupJoin;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StudygroupJoinMapper {

    /**
     * 스터디 멤버 리스트 & 가입 대기 리스트 매퍼
     * @param studygroupJoinList
     * @return
     */
    default StudygroupJoinDto.Response studygroupJoinToStudygroupJoinDtos(List<StudygroupJoin> studygroupJoinList) {
        StudygroupJoinDto.Response studygroupJoinDto = new StudygroupJoinDto.Response();
        List<String> nickName = new ArrayList<>(studygroupJoinList.size());
        studygroupJoinList.stream().map(e -> nickName.add(e.getMember().getNickName()));
        studygroupJoinDto.setNickName(nickName);
        return studygroupJoinDto;
    }
}