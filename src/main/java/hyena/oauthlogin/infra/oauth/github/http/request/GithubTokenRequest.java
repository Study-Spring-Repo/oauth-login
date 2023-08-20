package hyena.oauthlogin.infra.oauth.github.http.request;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@Builder
@JsonNaming(SnakeCaseStrategy.class)
public record GithubTokenRequest(String clientId,
                                 String clientSecret,
                                 String code,
                                 String redirectUri
) {
}
