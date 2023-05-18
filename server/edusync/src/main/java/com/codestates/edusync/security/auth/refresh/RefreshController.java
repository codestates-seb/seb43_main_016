package com.codestates.edusync.security.auth.refresh;

import com.codestates.edusync.security.auth.jwt.JwtTokenizer;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.member.repository.MemberRepository;
import com.codestates.edusync.security.auth.token.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RefreshController {
    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping
    public ResponseEntity<String> refreshAccessToken(HttpServletRequest request) { // 리프레쉬 토큰 받으면 엑세스 토큰 재발급
        String refreshTokenHeader = request.getHeader("Refresh");
        if (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer ")) {
            String refreshToken = refreshTokenHeader.substring(7);
            try {
                Optional<RefreshToken> refreshTokenObj = refreshTokenRepository.findByRtk(refreshToken);
                if (!refreshTokenObj.isPresent()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token [redis]");
                }

                Jws<Claims> claims = jwtTokenizer.getClaims(refreshToken, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));

                String email = claims.getBody().getSubject();
                Optional<Member> optionalMember = memberRepository.findByEmail(email);

                if (optionalMember.isPresent()) {
                    Member member = optionalMember.get();
                    String accessToken = tokenService.delegateAccessToken(member);

                    return ResponseEntity.ok().header("Authorization", "Bearer " + accessToken).body("Access token refreshed");
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
}
