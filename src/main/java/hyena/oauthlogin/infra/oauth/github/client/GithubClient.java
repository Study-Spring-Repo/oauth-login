package hyena.oauthlogin.infra.oauth.github.client;

import hyena.oauthlogin.oauth.OauthInformation;
import hyena.oauthlogin.oauth.OauthType;
import hyena.oauthlogin.oauth.client.OauthClient;
import hyena.oauthlogin.infra.oauth.github.config.GithubOauthConfig;
import hyena.oauthlogin.infra.oauth.github.http.GithubHttpInterface;
import hyena.oauthlogin.infra.oauth.github.http.request.GithubTokenRequest;
import hyena.oauthlogin.infra.oauth.github.http.response.GithubInformationResponse;
import hyena.oauthlogin.infra.oauth.github.GithubOauthInformation;
import hyena.oauthlogin.infra.oauth.github.http.response.GithubTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hyena.oauthlogin.oauth.OauthType.GITHUB;

@RequiredArgsConstructor
@Component
public class GithubClient implements OauthClient {

    private final GithubHttpInterface httpInterface;
    private final GithubOauthConfig oauthConfig;

    @Override
    public OauthType oauthType() {
        return GITHUB;
    }

    @Override
    public OauthInformation oauthInformation(final String authCode) {
        final GithubTokenResponse githubTokenResponse = httpInterface.fetchToken(githubTokenRequest(authCode));
        final GithubInformationResponse response = httpInterface.fetchInformation("Bearer " + githubTokenResponse.accessToken());

        return GithubOauthInformation.builder()
                .accessToken(githubTokenResponse.accessToken())
                .oauthId(response.id())
                .name(response.name())
                .build();
    }

    private GithubTokenRequest githubTokenRequest(final String authCode) {
        return GithubTokenRequest.builder()
                .redirectUri(oauthConfig.redirectUri())
                .clientId(oauthConfig.clientId())
                .clientSecret(oauthConfig.clientSecret())
                .code(authCode)
                .build();
    }
}
