package com.codestates.edusync.model.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class MemberDto {
    @Getter
    @NoArgsConstructor
    public static class Post{
        @Email(message = "올바른 이메일 형태가 아닙니다.")
        private String email;
        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        private String password;
        private String nickName;
    }

    @Getter
    @NoArgsConstructor
    public static class Patch {
        private String nickName;
        private String password;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileImage {
        private String profileImage;
    }

    @Getter
    @NoArgsConstructor
    public static class Detail{
        private String withMe;
        private String aboutMe;
    }

    @Getter
    @NoArgsConstructor
    public static class CheckPassword{
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class UuidListRequest {
        private List<String> data;

    }

}
