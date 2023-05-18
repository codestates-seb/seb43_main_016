package com.codestates.edusync.security.auth.refresh;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@RedisHash(value = "refreshToken", timeToLive = 25200)
public class RefreshToken {

    @Id
    private String rtk;
    private Long memberId;

    public RefreshToken(final String rtk, final Long memberId) {
        this.rtk = rtk;
        this.memberId = memberId;
    }

    public String getRefreshToken() {
        return rtk;
    }

    public Long getMemberId() {
        return memberId;
    }
}
