package com.codestates.edusync.util;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class JwtUtil {

    @Value("${jwt.key}")
    private String secretKey;

    public String extractEmailFromToken(String token) {
        // "Bearer " 부분 제거 (토큰만 남김)
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }else{
            throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN, "토큰 헤더값이 'Bearer '이 아닙니다.");
        }

        // secretKey를 Base64로 인코딩
        String encodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));

        // 토큰에서 claims 가져오기
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(encodedSecretKey)
                .parseClaimsJws(token);

        // 이메일 값 반환
        return claimsJws.getBody().get("email", String.class);
    }
}
