package hyena.oauthlogin.infra.oauth.github.http;

import hyena.oauthlogin.infra.oauth.github.http.request.GithubTokenRequest;
import hyena.oauthlogin.infra.oauth.github.http.response.GithubInformationResponse;
import hyena.oauthlogin.infra.oauth.github.http.response.GithubTokenResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface GithubHttpInterface {

    @PostExchange(url = "https://github.com/login/oauth/access_token", accept = APPLICATION_JSON_VALUE)
    GithubTokenResponse fetchToken(@RequestBody GithubTokenRequest request);

    @GetExchange(url = "https://api.github.com/user")
    GithubInformationResponse fetchInformation(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}
