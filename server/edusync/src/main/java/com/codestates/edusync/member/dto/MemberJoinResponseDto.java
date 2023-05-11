package com.codestates.edusync.member.dto;

import com.codestates.edusync.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MemberJoinResponseDto {
    private String email;
    private String profileImage;
    private String nickName;
    private String aboutMe;
    private String withMe;
    private Member.MemberStatus memberStatus;
    private List<String> roles;
}
