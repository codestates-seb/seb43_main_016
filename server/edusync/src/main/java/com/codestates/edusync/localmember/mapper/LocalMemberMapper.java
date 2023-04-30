package com.codestates.edusync.localmember.mapper;

import com.codestates.edusync.localmember.dto.LocalMemberDto;
import com.codestates.edusync.localmember.entity.LocalMember;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalMemberMapper {
    LocalMember localMemberPostToLocalMember(LocalMemberDto.Post requestBody);
    LocalMember localMemberPatchToLocalMember(LocalMemberDto.Patch requestBody);
}
