package com.codestates.edusync.model.member.controller;

import com.codestates.edusync.model.common.dto.MultiResponseDto;
import com.codestates.edusync.model.common.utils.MemberVerificationService;
import com.codestates.edusync.model.member.service.MemberService;
import com.codestates.edusync.model.member.dto.MemberDto;
import com.codestates.edusync.model.member.dto.MemberJoinResponseDto;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.member.mapper.MemberMapper;
import com.codestates.edusync.model.common.utils.UriCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/members")
@Validated // 유효성 검사용
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final MemberVerificationService memberVerificationService;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        Member member = memberMapper.memberPostToMember(requestBody);
        Member createMember = memberService.createMember(member);
        MemberJoinResponseDto responseDto = memberMapper.memberToMemberResponse(createMember);

        URI location = UriCreator.createUri("/members", createMember.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity(responseDto, headers, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity patchMember(
            @Valid @RequestBody MemberDto.Patch requestBody,
            Authentication authentication) {
        Member updateMember = memberService.updateMember(memberMapper.memberPatchToMember(requestBody), authentication.getName());
        MemberJoinResponseDto responseDto = memberMapper.memberToMemberResponse(updateMember);

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMember(Authentication authentication) {
        Member member = memberVerificationService.get(authentication.getName());
        MemberJoinResponseDto responseDto = memberMapper.memberToMemberResponse(member);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity getMembersByUUID(@RequestBody MemberDto.UuidListRequest uuidListRequestDto) {
        List<Member> members = memberVerificationService.getList(uuidListRequestDto.getData());
        List<MemberJoinResponseDto> responseDtos = members.stream()
                .map(memberMapper::memberToMemberResponse)
                .collect(Collectors.toList());
        return new ResponseEntity(responseDtos, HttpStatus.OK);
    }


    @GetMapping("/all") // Todo 개발용으로 만들어 뒀다. 후에 삭제!!
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
        List<Member> members = pageMembers.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(memberMapper.membersToMemberResponses(members),
                        pageMembers),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteMember(Authentication authentication) {
        memberService.deleteMember(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/profile-image")
    public ResponseEntity updateProfileImage(
            @RequestBody MemberDto.ProfileImage requestBody,
            Authentication authentication) {
        Member updatedMember = memberService.updateMember(memberMapper.memberProfileImageToMember(requestBody), authentication.getName());
        MemberJoinResponseDto responseDto = memberMapper.memberToMemberResponse(updatedMember);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/detail")
    public ResponseEntity updateDetail(@RequestBody MemberDto.Detail requestBody,
                                       Authentication authentication){
        Member updatedMember = memberService.updateMember(memberMapper.memberDetailToMember(requestBody), authentication.getName());
        MemberJoinResponseDto responseDto = memberMapper.memberToMemberResponse(updatedMember);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @PostMapping("/password")
    public ResponseEntity checkPassword(@RequestBody MemberDto.CheckPassword requestBody,
                                        Authentication authentication) {
        boolean isPasswordCorrect = memberService.checkPassword(requestBody.getPassword(), authentication.getName());
        if (isPasswordCorrect) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
