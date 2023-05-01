package com.codestates.edusync.member.mapper;

import com.codestates.edusync.member.dto.MemberDto;
import com.codestates.edusync.member.dto.MemberJoinResponseDto;
import com.codestates.edusync.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") // unmappedTargetPolicy = ReportingPolicy.IGNORE // Todo 테스트 끝나면 추가
public interface MemberMapper {
    Member memberPostToMember(MemberDto.PostMember requestBody);
    Member memberPatchToMember(MemberDto.Patch requestBody);
    MemberJoinResponseDto memberToMemberResponse(Member member);
    List<MemberJoinResponseDto> membersToMemberResponses(List<Member> members);
}
