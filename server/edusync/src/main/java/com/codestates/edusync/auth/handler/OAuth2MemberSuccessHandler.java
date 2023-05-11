package com.codestates.edusync.auth.handler;

import com.codestates.edusync.auth.jwt.JwtTokenizer;
import com.codestates.edusync.auth.utils.CustomAuthorityUtils;
import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

//  OAuth 2 인증 후, Frontend 애플리케이션 쪽으로 JWT를 전송하는 역할
@Slf4j
@Component
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberRepository memberRepository;

    public OAuth2MemberSuccessHandler(JwtTokenizer jwtTokenizer,
                                      CustomAuthorityUtils authorityUtils, MemberRepository memberRepository) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberRepository = memberRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User)authentication.getPrincipal();
//        String email = String.valueOf(oAuth2User.getAttributes().get("email")); // OAuth2User 객체로부터 Resource Owner의 이메일 주소를 얻기
//        String nickName = String.valueOf(oAuth2User.getAttributes().get("name")); // 이름을 얻기
//        String profileImage = String.valueOf(oAuth2User.getAttributes().get("picture")); // 프로필 이미지 URL을 얻기

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String providerType = authToken.getAuthorizedClientRegistrationId();

        String email;
        String nickName;
        String profileImage;

        if ("google".equals(providerType)) {
            email = String.valueOf(oAuth2User.getAttributes().get("email"));
            nickName = String.valueOf(oAuth2User.getAttributes().get("name"));
            profileImage = String.valueOf(oAuth2User.getAttributes().get("picture"));
        } else if ("kakao".equals(providerType)) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) kakaoAccount.get("email");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            nickName = (String) profile.get("nickname");
            profileImage = (String) profile.get("profile_image_url");
        } else if ("naver".equals(providerType)) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> response2 = (Map<String, Object>) attributes.get("response");
            email = (String) response2.get("email");
            nickName = (String) response2.get("name");
            profileImage = (String) response2.get("profile_image");
        } else {
            throw new IllegalArgumentException("Unsupported provider: " + providerType);
        }

        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        Member member;
        if (optionalMember.isEmpty()) { // 이메일이 저장되어 있지 않은 경우
            member = saveMember(email, nickName, profileImage); // Resource Owner의 정보를 DB에 저장
        } else {
            member = optionalMember.get();
        }

        redirect(request, response, member);  // Access Token과 Refresh Token을 생성해서 Frontend 애플리케이션에 전달하기 위해 Redirect
    }

    private Member saveMember(String email, String nickname, String profileImage) {
        memberRepository.findByEmail(email).ifPresent(it ->
        {throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS, String.format("%s is duplicated 버그발생! OAuth2 핸들러 검사하시오.", email));
        });
        Member member = new Member();
        member.setEmail(email);
        member.setNickName(nickname);
        member.setProfileImage(profileImage);
        List<String> roles = authorityUtils.createRoles(email);
        member.setRoles(roles);
        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, Member member) throws IOException {
        String accessToken = delegateAccessToken(member);  // JWT Access Token 생성
        String refreshToken = delegateRefreshToken(member.getEmail());  // Refresh Token 생성

        response.setHeader("Authorization", "Bearer " + accessToken);  // 클리이언트한테 Access Token 보내주기 (이후에 클라이언트 측에서 백엔드 애플리케이션 측에 요청을 보낼 때마다 request header에 추가해서 클라이언트 측의 자격을 증명하는데 사용)
        response.setHeader("Refresh", "Bearer " + refreshToken);                   // 클리이언트한테 Refresh Token 보내주기

        String uri = createURI(accessToken, refreshToken).toString();   // Access Token과 Refresh Token을 포함한 URL을 생성
        getRedirectStrategy().sendRedirect(request, response, uri);   // Frontend 애플리케이션 쪽으로 리다이렉트

        log.info("# Authenticated successfully!");

        // response 헤더 정보 로그 출력
        for (String headerName : response.getHeaderNames()) {
            log.info(headerName + ": " + response.getHeader(headerName));
        }
    }

    private String delegateAccessToken(Member member) { // Todo 코드의 중복 어떻게 해결할까?
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", member.getEmail());
        claims.put("nickName", member.getNickName());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();

        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(String username) {
        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost") // Todo 리액트 서버 도메인 주소 입력
                .port(8080)
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
