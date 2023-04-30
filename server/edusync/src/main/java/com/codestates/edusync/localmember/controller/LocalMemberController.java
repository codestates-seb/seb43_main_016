package com.codestates.edusync.localmember.controller;

import com.codestates.edusync.localmember.dto.LocalMemberDto;
import com.codestates.edusync.localmember.mapper.LocalMemberMapper;
import com.codestates.edusync.localmember.service.LocalMemberService;
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

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody LocalMemberDto requestBody) {

        return new ResponseEntity(null, null, HttpStatus.CREATED);
    }

    @PatchMapping("/{local-member-id}") // 케법 케이스 (url 경로에서 주로 사용하는 방식 => 필드값은 카멜케이스로 작성하니까 구분을 위해 사용)
    public ResponseEntity patchMember(
            @PathVariable("local-member-id") @Positive long memberId,
            @Valid @RequestBody LocalMemberDto requestBody,
            @RequestHeader("Authorization") String token) {

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping("/{local-member-id}")
    public ResponseEntity getMember(
            @PathVariable("local-member-id") @Positive long memberId) {
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        return new ResponseEntity<>(null, null, HttpStatus.OK);
    }

    @DeleteMapping("/{local-member-id}")
    public ResponseEntity deleteMember(
            @PathVariable("local-member-id") @Positive long memberId, @RequestHeader("Authorization") String token) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{local-member-id}/profile-image")
    public ResponseEntity updateProfileImage(
            @PathVariable("local-member-id") @Positive long memberId,
            @RequestBody LocalMemberDto requestBody,
            @RequestHeader("Authorization") String token) {
        return new ResponseEntity(null, HttpStatus.OK);
    }
}