package com.codestates.edusync.handler;

import com.codestates.edusync.security.auth.utils.ErrorResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        log.error("# Authentication failed: {}", exception.getMessage());

        if(exception.getMessage().equals("Member is not active")){
            ErrorResponder.sendErrorResponse(response, HttpStatus.FORBIDDEN, "Member is not active");
        }else {
            log.error("# Authentication failed: Please check your email and password again.");
            ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Please check your email and password again.");
        }
    }
}
