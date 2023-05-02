package com.codestates.edusync.auth.refresh;

import com.codestates.edusync.auth.jwt.JwtTokenizer;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/refresh")
@AllArgsConstructor
public class RefreshController {
    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;
    @PostMapping
    public ResponseEntity<String> refreshAccessToken(HttpServletRequest request) { // 리프레쉬 토큰 받으면 엑세스 토큰 재발급
        String refreshTokenHeader = request.getHeader("Refresh");
        if (refreshTokenHeader != null && refreshTokenHeader.startsWith("WishJWT ")) {
            String refreshToken = refreshTokenHeader.substring(8);
            try {
                Jws<Claims> claims = jwtTokenizer.getClaims(refreshToken, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));

                String email = claims.getBody().getSubject();
                Optional<Member> optionalMember = memberRepository.findByEmail(email);

                if (optionalMember.isPresent()) {
                    Member member = optionalMember.get();
                    String accessToken = delegateAccessToken(member);

                    return ResponseEntity.ok().header("Authorization", "WishJWT " + accessToken).body("Access token refreshed");
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid member email");
                }
            } catch (JwtException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing refresh token");
        }
    }

    private String delegateAccessToken(Member member) { // 코드의 중복...찝찝하다.
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
}
