package com.codestates.edusync.filter;

import com.codestates.edusync.security.auth.dto.LoginDto;
import com.codestates.edusync.security.auth.jwt.JwtTokenizer;
import com.codestates.edusync.model.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// 로그인 인증 요청을 처리하는 Custom Security Filter 구현
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {  // 디폴트 Security Filter인 UsernamePasswordAuthenticationFilter를 확장해서 구현
    private final AuthenticationManager authenticationManager;
    // DI 받은 AuthenticationManager는 로그인 인증 정보(Username/Password)를 전달받아 UserDetailsService와 인터랙션 한 뒤 인증 여부를 판단
    private final JwtTokenizer jwtTokenizer;
    // DI 받은 JwtTokenizer는 클라이언트가 인증에 성공할 경우, JWT를 생성 및 발급하는 역할

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenizer jwtTokenizer) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenizer = jwtTokenizer;
    }

    @SneakyThrows // 예외처리 무시
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) { // 인증을 시도하는 로직을 구현
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class); // 역직렬화(Deserialization)

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()); // Username과 Password 정보를 포함한 UsernamePasswordAuthenticationToken 생성

        return authenticationManager.authenticate(authenticationToken);  // UsernamePasswordAuthenticationToken을 AuthenticationManager에게 전달하면서 인증 처리를 위임
    }

    // 인증에 성공할 경우 (Spring Security에서 자동으로) 호출되는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        Member member = (Member) authResult.getPrincipal();  // 인증 정보로 Member 엔티티 객체 만들기

        String accessToken = delegateAccessToken(member);   // Access Token 생성
        String refreshToken = delegateRefreshToken(member); // Refresh Token 생성

        response.setHeader("Authorization", "Bearer " + accessToken);  // 클리이언트한테 Access Token 보내주기 (이후에 클라이언트 측에서 백엔드 애플리케이션 측에 요청을 보낼 때마다 request header에 추가해서 클라이언트 측의 자격을 증명하는데 사용)
        response.setHeader("Refresh", "Bearer " + refreshToken);                   // 클리이언트한테 Refresh Token 보내주기

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);  // MemberAuthenticationSuccessHandler의 onAuthenticationSuccess() 메서드 호출
        // 인증 성공 후에 할 동작을 설정해둔걸 불러와서 수행
        // 인증 실패 할 경우 MemberAuthenticationFailureHandler클래스의 onAuthenticationFailure() 메서드는 코드 추가 없이도 알아서 호출된다.
    }

    // Access Token을 생성하는 구체적인 로직
    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", member.getEmail());
        claims.put("roles", member.getRoles());
        claims.put("nickName", member.getNickName());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    // Refresh Token을 생성하는 구체적인 로직
    private String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}
