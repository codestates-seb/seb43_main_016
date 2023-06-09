package com.codestates.edusync.model.member.mapper;

import com.codestates.edusync.model.member.dto.MemberDto;
import com.codestates.edusync.model.member.dto.MemberJoinResponseDto;
import com.codestates.edusync.model.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE) // unmappedTargetPolicy = ReportingPolicy.IGNORE // Todo 테스트 끝나면 추가
public interface MemberMapper {
    Member memberPostToMember(MemberDto.Post requestBody);
    Member memberPatchToMember(MemberDto.Patch requestBody);
    Member memberProfileImageToMember(MemberDto.ProfileImage requestBody);
    Member memberDetailToMember(MemberDto.Detail requestBody);
    MemberJoinResponseDto memberToMemberResponse(Member member);
    List<MemberJoinResponseDto> membersToMemberResponses(List<Member> members);
}
