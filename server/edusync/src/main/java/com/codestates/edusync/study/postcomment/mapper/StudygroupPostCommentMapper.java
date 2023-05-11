package com.codestates.edusync.study.postcomment.mapper;

import com.codestates.edusync.study.postcomment.dto.StudygroupPostCommentDto;
import com.codestates.edusync.study.postcomment.dto.StudygroupPostCommentResponseDto;
import com.codestates.edusync.study.postcomment.entity.StudygroupPostComment;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudygroupPostCommentMapper {
    StudygroupPostComment studygroupPostCommentPostDtoToStudygroupPostComment(StudygroupPostCommentDto.Post postDto);
    StudygroupPostComment studygroupPostCommentPatchDtoToStudygroupPostComment(StudygroupPostCommentDto.Patch patchDto);

    @IterableMapping(qualifiedByName = "EntityToResponse")
    List<StudygroupPostCommentResponseDto> studygroupPostCommentToStudygroupPostCommentResponseDtos(List<StudygroupPostComment> comment);

    @Named("EntityToResponse")
    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "studygroup.id", target = "studygroupId")
    @Mapping(source = "member.id", target = "memberId")
    StudygroupPostCommentResponseDto studygroupPostCommentToStudygroupPostCommentResponseDto(StudygroupPostComment comment);
}
