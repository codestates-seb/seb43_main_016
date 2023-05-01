package com.codestates.edusync.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberJoinResponseDto {
    private long id;
    private String email;
    private String profileImage;
    private String nickName;
}
