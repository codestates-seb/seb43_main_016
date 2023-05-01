package com.codestates.edusync.member.controller;

import com.codestates.edusync.dto.MultiResponseDto;
import com.codestates.edusync.member.dto.MemberDto;
import com.codestates.edusync.member.dto.MemberJoinResponseDto;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.mapper.MemberMapper;
import com.codestates.edusync.member.service.MemberService;
import com.codestates.edusync.util.UriCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/members")
@Validated // 유효성 검사용
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.PostMember requestBody) {
        Member member = memberMapper.memberPostToMember(requestBody);
        Member createMember = memberService.createMember(member);
        MemberJoinResponseDto responseDto = memberMapper.memberToMemberResponse(createMember);

        URI location = UriCreator.createUri("/members", createMember.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity(responseDto, headers, HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}") // 케법 케이스 (url 경로에서 주로 사용하는 방식 => 필드값은 카멜케이스로 작성하니까 구분을 위해 사용)
    public ResponseEntity patchMember(
            @PathVariable("member-id") @Positive long memberId,
            @Valid @RequestBody MemberDto.Patch requestBody) {
        requestBody.setId(memberId);
        Member updateMember = memberService.updateMember(memberMapper.memberPatchToMember(requestBody));
        MemberJoinResponseDto responseDto = memberMapper.memberToMemberResponse(updateMember);

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(
            @PathVariable("member-id") @Positive long memberId) {
        Member member = memberService.findMember(memberId);
        MemberJoinResponseDto responseDto = memberMapper.memberToMemberResponse(member);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
        List<Member> members = pageMembers.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(memberMapper.membersToMemberResponses(members),
                        pageMembers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(
            @PathVariable("member-id") @Positive long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{member-id}/profile-image")
    public ResponseEntity updateProfileImage(
            @PathVariable("member-id") @Positive long memberId,
            @RequestBody MemberDto.ProfileImage requestBody) {
        Member member = memberService.findMember(memberId);
        member.setProfileImage(requestBody.getProfileImage());
        Member updatedMember = memberService.updateMember(member);
        MemberJoinResponseDto responseDto = memberMapper.memberToMemberResponse(updatedMember);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }
}
