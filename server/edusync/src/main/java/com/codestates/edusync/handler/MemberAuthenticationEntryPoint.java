package com.codestates.edusync.handler;

import com.codestates.edusync.security.auth.utils.ErrorResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// AuthenticationException(인증오류)이 발생할 때 호출되는 핸들러 같은 역할
@Slf4j
@Component
public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override // 인증 요청이 실패했을 때 호출되는 메서드
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Exception exception = (Exception) request.getAttribute("exception"); // 어떤 오류인지 exception에 할당 (필터에서 저장했던 request의 Attribute 중 exception)
        ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Authentication error occurred."); // 클라이언트에게 401 응답 보내기

        logExceptionMessage(authException, exception); // (인증 과정에서 발생한 예외 정보 or 요청 객체에서 얻어온 예외 정보) log로 남기기
    }

    private void logExceptionMessage(AuthenticationException authException, Exception exception) {
        String message = exception != null ? exception.getMessage() : authException.getMessage(); // exception이 null이 아니면 전자, null이면 후자를 message에 할당
        log.warn("Unauthorized error happened: {}", message);
    }
}
