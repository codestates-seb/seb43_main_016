package com.codestates.edusync.auth.handler;

import com.codestates.edusync.response.ErrorResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {  // 로그인 인증 실패 시, 추가 작업을 할 수 있는 클래스
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        // 인증 실패 시, 에러 로그를 기록하거나 error response를 전송할 수 있다.
        log.error("# Authentication failed: {}", exception.getMessage());

        sendErrorResponse(response);  // 출력 스트림에 Error 정보 담기
    }

    public void sendErrorResponse(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();     // Error 정보가 담긴 객체(ErrorResponse)를 JSON 문자열로 변환하기 위해 생성
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED); // HttpStatus.UNAUTHORIZED(401) = 인증 실패 HTTP status
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);    // response의 Content Type이 “application/json” 이라고 클라이언트한테 알려주기
        response.setStatus(HttpStatus.UNAUTHORIZED.value());          // response의 status가 401이라고 클라이언트에게 알려주기
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));   // Gson을 이용해 ErrorResponse 객체를 JSON 포맷 문자열로 변환 후, 출력 스트림을 생성
    }
}
