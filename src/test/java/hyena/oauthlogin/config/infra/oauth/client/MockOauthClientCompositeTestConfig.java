package hyena.oauthlogin.config.infra.oauth.client;

import hyena.oauthlogin.config.infra.oauth.oauthinformation.MockHyenaInformation;
import hyena.oauthlogin.config.infra.oauth.oauthinformation.MockMemberInformation;
import hyena.oauthlogin.oauth.OauthType;
import hyena.oauthlogin.oauth.client.OauthClientComposite;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.when;

@Profile("test")
@TestConfiguration
public abstract class MockOauthClientCompositeTestConfig {

    @Bean
    public OauthClientComposite oauthClientComposite() {
        final var mock = Mockito.mock(OauthClientComposite.class);
        when(mock.findOauthInformation(OauthType.GITHUB, "member_auth_code")).thenReturn(new MockMemberInformation());
        when(mock.findOauthInformation(OauthType.GITHUB, "hyena_auth_code")).thenReturn(new MockHyenaInformation());

        return mock;
    }
}
