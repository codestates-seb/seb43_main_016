package com.codestates.edusync.model.study.postcomment.mapper;

import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.postcomment.entity.StudygroupPostComment;
import com.codestates.edusync.model.study.postcomment.dto.StudygroupPostCommentDto;
import com.codestates.edusync.model.study.postcomment.dto.StudygroupPostCommentResponseDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StudygroupPostCommentMapper {
    StudygroupPostComment studygroupPostCommentPostDtoToStudygroupPostComment(StudygroupPostCommentDto.Post postDto);
    StudygroupPostComment studygroupPostCommentPatchDtoToStudygroupPostComment(StudygroupPostCommentDto.Patch patchDto);

    default List<StudygroupPostCommentResponseDto> studygroupPostCommentToStudygroupPostCommentResponseDtos(List<StudygroupPostComment> comments,
                                                                                                            String emailOfLoginMember) {
        List<StudygroupPostCommentResponseDto> result = new ArrayList<>();
        comments.forEach(
                comment -> {
                    StudygroupPostCommentResponseDto resultResponse =
                            studygroupPostCommentToStudygroupPostCommentResponseDto(comment);
                    resultResponse.setIsMyComment(comment.getMember().getEmail().equals(emailOfLoginMember));
                    result.add(resultResponse);
                }
        );

        return result;
    };

    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "studygroup.id", target = "studygroupId")
    @Mapping(source = "member.nickName", target = "nickName")
    StudygroupPostCommentResponseDto studygroupPostCommentToStudygroupPostCommentResponseDto(StudygroupPostComment comment);
}
