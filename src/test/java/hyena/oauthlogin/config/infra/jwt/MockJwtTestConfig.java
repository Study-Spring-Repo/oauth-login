package hyena.oauthlogin.config.infra.jwt;

import hyena.oauthlogin.infra.jwt.JwtDecoder;
import hyena.oauthlogin.infra.jwt.JwtEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("test")
@TestConfiguration
public abstract class MockJwtTestConfig {

    @Bean
    public JwtEncoder jwtEncoder() {
        return new JwtEncoder(new MockJwtConfig());
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return new JwtDecoder(new MockJwtConfig());
    }
}
