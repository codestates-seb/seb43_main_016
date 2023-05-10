package com.codestates.edusync.auth.oauth2;

import com.codestates.edusync.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class OAuth2Attributes {

    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String email;
    private String nickName;
    private String profileImage;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes, String email, String nickName, String profileImage) {
        this.attributes = attributes;
        this.email = email;
        this.nickName = nickName;
        this.profileImage = profileImage;
    }

    public static OAuth2Attributes of(String registrationId, Map<String, Object> attributes){
        if(registrationId.equals("naver")) {
            return ofNaver(attributes);
        } else if (registrationId.equals("kakao")) {
            return ofKakao(attributes);
        } else {
            return ofGoogle(attributes);
        }
    }

    private static OAuth2Attributes ofNaver(Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuth2Attributes.builder()
                .email((String) response.get("email"))
                .nickName((String) response.get("name"))
                .profileImage((String) response.get("profile_image"))
                .attributes(attributes)
                .build();
    }

    private static OAuth2Attributes ofKakao(Map<String, Object> attributes) {

        LinkedHashMap<String, Object> kakaoAccount = (LinkedHashMap)attributes.get("kakao_account");
        LinkedHashMap<String, Object> profile = (LinkedHashMap) kakaoAccount.get("profile");
        return OAuth2Attributes.builder()
                .email((String)kakaoAccount.get("email"))
                .nickName((String)profile.get("nickname"))
                .profileImage((String)profile.get("image"))
                .attributes(attributes)
                .build();
    }

    private static OAuth2Attributes ofGoogle(Map<String, Object> attributes) {

        return OAuth2Attributes.builder()
                .email((String) attributes.get("email"))
                .nickName((String) attributes.get("name"))
                .profileImage((String) attributes.get("picture"))
                .attributes(attributes)
                .build();
    }


    public Member toEntity() {
        return Member.builder()
                .email(email)
                .nickName(nickName)
                .profileImage(profileImage)
                .build();
    }
}
