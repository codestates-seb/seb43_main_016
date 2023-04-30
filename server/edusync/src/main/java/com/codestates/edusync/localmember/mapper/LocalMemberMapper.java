package com.codestates.edusync.localmember.mapper;

import com.codestates.edusync.localmember.dto.LocalMemberDto;
import com.codestates.edusync.localmember.dto.LocalMemberResponseDto;
import com.codestates.edusync.localmember.entity.LocalMember;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring") // unmappedTargetPolicy = ReportingPolicy.IGNORE // 나중에 추가 (매핑안되는 요소 오류발생 안시키도록 함)
public interface LocalMemberMapper {
    LocalMember localMemberPostToLocalMember(LocalMemberDto.Post requestBody);
    LocalMember localMemberPatchToLocalMember(LocalMemberDto.Patch requestBody);
    LocalMemberResponseDto localMemberToLocalMemberResponse(LocalMember localMember);
    List<LocalMemberResponseDto> localMembersToLocalMemberResponse(List<LocalMember> localMembers);
}
