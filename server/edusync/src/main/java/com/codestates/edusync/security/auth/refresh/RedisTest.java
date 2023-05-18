package com.codestates.edusync.security.auth.refresh;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisTest {
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping
    public ResponseEntity<String> redisTest(HttpServletRequest request) {

        RefreshToken rtk = new RefreshToken("111", 111L);
        refreshTokenRepository.save(rtk);

        List<RefreshToken> test= refreshTokenRepository.findAll();

        Optional<RefreshToken> refreshTokenObj = refreshTokenRepository.findById("111");

        return ResponseEntity.ok().body(refreshTokenObj.get().getRefreshToken());
    }
}
