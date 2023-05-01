package com.codestates.edusync.auth.filter;

import com.codestates.edusync.auth.jwt.JwtTokenizer;
import com.codestates.edusync.auth.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
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

import static com.codestates.edusync.audit.utils.ErrorResponder.sendErrorResponse;

// JWT 검증 필터 구현 클래스
// 클라이언트 측에서 전송된 request header에 포함된 JWT에 대해 검증 작업을 수행하는 코드
public class JwtVerificationFilter extends OncePerRequestFilter {  // OncePerRequestFilter 상속받아서 request 당 단 한 번만 수행
    private final JwtTokenizer jwtTokenizer; // JWT를 검증하고 Claims(토큰에 포함된 정보)를 얻는 데 사용
    private final CustomAuthorityUtils authorityUtils; // JWT 검증에 성공하면 Authentication 객체에 채울 사용자의 권한을 생성하는 데 사용

    public JwtVerificationFilter(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> claims = verifyJws(request); // JWT 검증
        setAuthenticationToContext(claims);      // SecurityContext에 검증된 정보 저장
        filterChain.doFilter(request, response); // 인증 끝났으니 다음 작업하도록 다음 필터 호출
    }

    // 특정 조건에 부합하면(true이면) 해당 Filter의 동작을 수행하지 않고 다음 Filter로 건너뛰도록 해주는 메서드인 OncePerRequestFilter의 shouldNotFilter()를 오버라이드
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("WishJWT");  //  header의 값이 null이거나 WishJWT로 시작하지 않는다면 해당 Filter의 동작을 수행하지 않도록 정의
        // JWT가 Authorization header에 포함되지 않았다면 JWT 자격증명이 필요하지 않은 리소스에 대한 요청이라고 판단하고 다음(Next) Filter로 처리를 넘기는 것
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) { // JWT를 검증하는데 사용되는 메서드
        String jws = request.getHeader("Authorization").replace("WishJWT ", ""); // "WishJWT" 부분을 제거해서 JWT(accessToken) 얻기
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); // JWT 서명(Signature)을 검증하기 위한 Secret Key 얻기
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();   // Claims를 파싱

        return claims; // Claims가 정상적으로 파싱이 되면 서명 검증에 성공한거다.
    }

    private void setAuthenticationToContext(Map<String, Object> claims) { //  Authentication 객체를 SecurityContext에 저장하기 위한 메서드
        String username = (String) claims.get("memberEmail");   // 파싱한 Claims에서 username 얻기
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));  //  Claims에서 권한 정보 얻기
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);  // 이미 앞에서 인증된 Authentication객체를 생성하는데 비밀번호는 불필요하니까 null입력
        SecurityContextHolder.getContext().setAuthentication(authentication); //  SecurityContext에 Authentication 객체를 저장
    }

}
