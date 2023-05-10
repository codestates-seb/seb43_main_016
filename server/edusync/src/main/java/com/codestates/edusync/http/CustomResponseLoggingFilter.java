package com.codestates.edusync.http;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomResponseLoggingFilter extends GenericFilterBean { // http 요청, 응답을 디버깅하기 위해 log를 남기는 필터

    private static final Logger logger = LoggerFactory.getLogger(CustomResponseLoggingFilter.class);
    private static final Gson gson = new Gson();
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        ContentCachingResponseWrapper wrappedResponse
                = new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(request, wrappedResponse);

        logResponse(wrappedResponse);
        wrappedResponse.copyBodyToResponse();
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        String responseBody = new String(response.getContentAsByteArray());

        // JSON 형식으로 변환하고 로그로 출력합니다.
        String json = gson.toJson(responseBody);
        logger.info("Response: {}", json);
    }
}
