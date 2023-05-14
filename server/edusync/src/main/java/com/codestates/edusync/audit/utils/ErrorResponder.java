package com.codestates.edusync.audit.utils;

import com.codestates.edusync.exception.ErrorResponse;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorResponder {
    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(status, message);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 응답의 컨텐츠 타입을 JSON으로 설정
        response.setStatus(status.value()); // status 작성
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class)); // response body 부분 작성
    }


}
