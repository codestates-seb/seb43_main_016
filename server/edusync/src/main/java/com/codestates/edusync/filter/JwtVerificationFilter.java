package com.codestates.edusync.filter;

import com.codestates.edusync.security.auth.jwt.JwtTokenizer;
import com.codestates.edusync.security.auth.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.codestates.edusync.security.auth.utils.ErrorResponder.sendErrorResponse;

// JWT 검증 필터
// 클라이언트 측에서 전송된 request header에 포함된 JWT에 대해 검증 작업을 수행하는 코드
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
            filterChain.doFilter(request, response);
        } catch (SignatureException se) {
            sendErrorResponse(response, HttpStatus.valueOf(401), "The token information is incorrect.");
        } catch (MalformedJwtException me){
            sendErrorResponse(response, HttpStatus.valueOf(401), "JWT strings must contain exactly 2 period characters");
        } catch (ExpiredJwtException ee) {
            sendErrorResponse(response, HttpStatus.valueOf(401), "The token has expired.");
        } catch (Exception e) {
            request.setAttribute("exception", e);
            filterChain.doFilter(request, response);
        }
    }

    // 특정 조건에 부합하면(true이면) 해당 Filter(JwtVerificationFilter)의 동작을 수행하지 않고 다음 Filter로 건너뛰도록 해주는 메서드인 OncePerRequestFilter의 shouldNotFilter()를 오버라이드
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String accessToken = request.getHeader("Authorization");

        return accessToken == null;
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) { // JWT를 검증하는데 사용되는 메서드
        if(!request.getHeader("Authorization").startsWith("Bearer ")) {
            throw new SignatureException("");
        }
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) { //  Authentication 객체를 SecurityContext에 저장하기 위한 메서드
        String username = (String) claims.get("email");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
