package com.codestates.edusync.study.studygroupjoin.mapper;

import com.codestates.edusync.study.studygroupjoin.dto.StudygroupJoinDto;
import com.codestates.edusync.study.studygroupjoin.entity.StudygroupJoin;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudygroupJoinMapper {

    default StudygroupJoinDto.Response studygroupJoinToStudygroupJoinDto(StudygroupJoin studygroupJoin) {
        StudygroupJoinDto.Response studygroupJoinDto = new StudygroupJoinDto.Response();
        studygroupJoinDto.setNickName(studygroupJoin.getMember().getNickName());
        return studygroupJoinDto;
    }

    default List<StudygroupJoinDto.Response> studygroupJoinToStudygroupJoinDtos(List<StudygroupJoin> studygroupJoinList) {
        return studygroupJoinList.stream().map(this::studygroupJoinToStudygroupJoinDto).collect(Collectors.toList());
    }
}