package com.codestates.edusync.study.postcomment.mapper;

import com.codestates.edusync.study.postcomment.dto.StudygroupPostCommentDto;
import com.codestates.edusync.study.postcomment.dto.StudygroupPostCommentResponseDto;
import com.codestates.edusync.study.postcomment.entity.StudygroupPostComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudygroupPostCommentMapper {
    StudygroupPostComment studygroupPostCommentPostDtoToStudygroupPostComment(StudygroupPostCommentDto.Post postDto);
    StudygroupPostComment studygroupPostCommentPatchDtoToStudygroupPostComment(StudygroupPostCommentDto.Patch patchDto);

    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "studygroup.id", target = "studygroupId")
    @Mapping(source = "member.id", target = "memberId")
    List<StudygroupPostCommentResponseDto> studygroupPostCommentToStudygroupPostCommentResponseDtos(List<StudygroupPostComment> comment);
}
