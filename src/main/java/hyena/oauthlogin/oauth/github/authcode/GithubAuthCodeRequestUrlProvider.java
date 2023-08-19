package hyena.oauthlogin.oauth.github.authcode;

import hyena.oauthlogin.infra.oauth.OauthType;
import hyena.oauthlogin.infra.oauth.authcode.AuthCodeRequestUrlProvider;
import hyena.oauthlogin.oauth.github.config.GithubOauthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static hyena.oauthlogin.infra.oauth.OauthType.*;

@RequiredArgsConstructor
@Component
public class GithubAuthCodeRequestUrlProvider implements AuthCodeRequestUrlProvider {

    private final GithubOauthConfig githubOauthConfig;

    @Override
    public OauthType oauthType() {
        return GITHUB;
    }

    @Override
    public String requestUrl() {
        return UriComponentsBuilder
                .fromUriString("https://github.com/login/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", githubOauthConfig.clientId())
                .queryParam("redirect_uri", githubOauthConfig.redirectUri())
                .queryParam("scope", String.join(",", githubOauthConfig.scope()))
                .toUriString();
    }
}
