package com.codestates.edusync.model.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberResponseDto {
    private Long id;

    private String nickName;
    private String email;
    private String grade;

    private String aboutMe;
    private String withMe;
}
