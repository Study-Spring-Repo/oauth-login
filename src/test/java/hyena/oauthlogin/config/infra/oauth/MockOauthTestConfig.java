package hyena.oauthlogin.config.infra.oauth;

import hyena.oauthlogin.config.infra.jwt.MockJwtTestConfig;
import hyena.oauthlogin.config.infra.oauth.authcode.MockAuthCodeRequestUrlCompositeTestConfig;
import hyena.oauthlogin.config.infra.oauth.client.MockOauthClientCompositeTestConfig;
import hyena.oauthlogin.infra.jwt.JwtDecoder;
import hyena.oauthlogin.infra.jwt.JwtEncoder;
import hyena.oauthlogin.oauth.application.OauthService;
import hyena.oauthlogin.oauth.authcode.AuthCodeRequestUrlProviderComposite;
import hyena.oauthlogin.oauth.client.OauthClientComposite;
import hyena.oauthlogin.oauth.domain.OauthMemberRepository;
import hyena.oauthlogin.oauth.domain.RefreshTokenRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Import({MockAuthCodeRequestUrlCompositeTestConfig.class,
        MockOauthClientCompositeTestConfig.class,
        MockJwtTestConfig.class})
@TestConfiguration
public abstract class MockOauthTestConfig {

    @Bean
    public OauthService oauthService(
            @Autowired AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite,
            @Autowired OauthClientComposite oauthClientComposite,
            @Autowired OauthMemberRepository oauthMemberRepository,
            @Autowired RefreshTokenRedisRepository refreshTokenRedisRepository,
            @Autowired JwtEncoder jwtEncoder,
            @Autowired JwtDecoder jwtDecoder
    ) {
        return new OauthService(
                authCodeRequestUrlProviderComposite,
                oauthClientComposite,
                oauthMemberRepository,
                refreshTokenRedisRepository,
                jwtEncoder,
                jwtDecoder
        );
    }
}
