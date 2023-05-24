package com.codestates.edusync.filter;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.security.auth.dto.LoginDto;
import com.codestates.edusync.security.auth.jwt.JwtTokenizer;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.security.auth.token.TokenService;
import com.codestates.edusync.security.auth.utils.ErrorResponder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
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

// 로그인 인증 요청을 처리하는 Custom Security Filter
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @SneakyThrows // 예외처리 무시
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) { // 인증을 시도하는 로직
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    // provider측(OAuth2) 인증에 성공할 경우 (Spring Security에서 자동으로) 호출되는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        Member member = (Member) authResult.getPrincipal();

        if(!member.getMemberStatus().equals(Member.MemberStatus.MEMBER_ACTIVE)){
            member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        }

        String accessToken = tokenService.delegateAccessToken(member);
        String refreshToken = tokenService.delegateRefreshToken(member);

        response.setHeader("Authorization", accessToken);
        response.setHeader("Refresh", refreshToken);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
