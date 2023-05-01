package com.codestates.edusync.localmember.controller;

import com.codestates.edusync.dto.MultiResponseDto;
import com.codestates.edusync.localmember.dto.LocalMemberDto;
import com.codestates.edusync.localmember.dto.LocalMemberResponseDto;
import com.codestates.edusync.localmember.entity.LocalMember;
import com.codestates.edusync.localmember.mapper.LocalMemberMapper;
import com.codestates.edusync.localmember.service.LocalMemberService;
import com.codestates.edusync.member.entity.Member;
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
@RequestMapping("/members/local")
@Validated // 유효성 검사용
@Slf4j
public class LocalMemberController {
    private final LocalMemberService localMemberService;
    private final LocalMemberMapper mapper;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody LocalMemberDto.Post requestBody) {
        LocalMember lm = mapper.localMemberPostToLocalMember(requestBody);
        LocalMember createlm = localMemberService.createLocalMember(lm);
        LocalMemberResponseDto responseDto = mapper.localMemberToLocalMemberResponse(createlm);

        memberService.createMember(createlm); // Todo 현재는 response에 담아주지 않음. 프론트 요청시 추가

        URI location = UriCreator.createUri("/members/local", createlm.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity(responseDto, headers, HttpStatus.CREATED);
    }

    @PatchMapping("/{local-member-id}") // 케법 케이스 (url 경로에서 주로 사용하는 방식 => 필드값은 카멜케이스로 작성하니까 구분을 위해 사용)
    public ResponseEntity patchMember(
            @PathVariable("local-member-id") @Positive long memberId,
            @Valid @RequestBody LocalMemberDto.Patch requestBody,
            @RequestHeader("Authorization") String token) {
        requestBody.setMemberId(memberId);
        LocalMember updateMember = localMemberService.updateLocalMember(mapper.localMemberPatchToLocalMember(requestBody));
        LocalMemberResponseDto responseDto = mapper.localMemberToLocalMemberResponse(updateMember);

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{local-member-id}")
    public ResponseEntity getMember(
            @PathVariable("local-member-id") @Positive long memberId) {
        LocalMember member = localMemberService.findLocalMember(memberId);
        LocalMemberResponseDto responseDto = mapper.localMemberToLocalMemberResponse(member);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<LocalMember> pageMembers = localMemberService.findLocalMembers(page - 1, size);
        List<LocalMember> members = pageMembers.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.localMembersToLocalMemberResponse(members),
                        pageMembers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{local-member-id}")
    public ResponseEntity deleteMember(
            @PathVariable("local-member-id") @Positive long memberId, @RequestHeader("Authorization") String token) {
        localMemberService.deleteLocalMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{local-member-id}/profile-image") // 이미지 하나만 바꿀 수 있도록 세팅
    public ResponseEntity updateProfileImage(
            @PathVariable("local-member-id") @Positive long memberId,
            @RequestBody LocalMemberDto.ProfileImage requestBody,
            @RequestHeader("Authorization") String token) {
        LocalMember member = localMemberService.findLocalMember(memberId);
        member.setProfileImage(requestBody.getProfileImage());
        LocalMember updatedMember = localMemberService.updateLocalMember(member);
        LocalMemberResponseDto responseDto = mapper.localMemberToLocalMemberResponse(updatedMember);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }
}