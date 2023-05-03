package com.codestates.edusync.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MemberJoinResponseDto {
    private long id;
    private String email;
    private String profileImage;
    private String nickName;
    private String aboutMe;
    private String withMe;
}
