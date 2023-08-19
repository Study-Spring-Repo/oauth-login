package hyena.oauthlogin.oauth.github.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.github")
public record GithubOauthConfig(String redirectUri,
                                String clientId,
                                String clientSecret,
                                String[] scope
) {
}
