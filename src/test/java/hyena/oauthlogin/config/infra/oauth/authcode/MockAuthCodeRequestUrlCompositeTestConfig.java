package hyena.oauthlogin.config.infra.oauth.authcode;

import hyena.oauthlogin.oauth.authcode.AuthCodeRequestUrlProviderComposite;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

@Profile("test")
@TestConfiguration
public abstract class MockAuthCodeRequestUrlCompositeTestConfig {

    @Bean
    public AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite() {
        final var mock = Mockito.mock(AuthCodeRequestUrlProviderComposite.class);
        when(mock.findRequestUrl(notNull())).thenReturn("https://test-redirect.test");

        return mock;
    }
}
