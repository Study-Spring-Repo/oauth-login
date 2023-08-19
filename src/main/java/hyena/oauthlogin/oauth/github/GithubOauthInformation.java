package hyena.oauthlogin.oauth.github;

import hyena.oauthlogin.infra.oauth.OauthInformation;
import lombok.Builder;

public class GithubOauthInformation implements OauthInformation {

    private final String accessToken;
    private final String oauthId;
    private final String name;

    @Builder
    protected GithubOauthInformation(final String accessToken, final String oauthId, final String name) {
        this.accessToken = accessToken;
        this.oauthId = oauthId;
        this.name = name;
    }

    @Override
    public String accessToken() {
        return accessToken;
    }

    @Override
    public String oauthId() {
        return oauthId;
    }

    @Override
    public String name() {
        return name;
    }
}
