package com.codestates.edusync.localmember.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LocalMemberResponseDto {
    private long id;
    private String email;
    private String profileImage;
    private String nickName;
}
