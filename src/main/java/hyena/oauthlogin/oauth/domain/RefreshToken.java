package hyena.oauthlogin.oauth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash(value = "refreshToken", timeToLive = 60)
public class RefreshToken {

    @Id
    private String refreshToken;

    private Long memberId;

    protected RefreshToken(final String refreshToken, final Long memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }

    public static RefreshToken newInstance(final Long memberId) {
        return new RefreshToken(UUID.randomUUID().toString(), memberId);
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getMemberId() {
        return memberId;
    }
}
