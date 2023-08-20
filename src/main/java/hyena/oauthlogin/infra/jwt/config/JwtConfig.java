package hyena.oauthlogin.infra.jwt.config;


import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@RequiredArgsConstructor
@ConfigurationProperties("jwt.token")
public class JwtConfig {

    private final String secretKey;
    private final String issuer;

    public Key getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String getIssuer() {
        return issuer;
    }
}
