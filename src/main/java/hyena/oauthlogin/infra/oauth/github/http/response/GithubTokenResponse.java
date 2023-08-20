package hyena.oauthlogin.infra.oauth.github.http.response;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@JsonNaming(SnakeCaseStrategy.class)
public record GithubTokenResponse(String accessToken,
                                  String tokenType
) {
}
