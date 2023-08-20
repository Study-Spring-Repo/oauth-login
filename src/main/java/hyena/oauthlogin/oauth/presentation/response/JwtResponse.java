package hyena.oauthlogin.oauth.presentation.response;

public record JwtResponse(String accessToken) {

    public static JwtResponse from(final String accessToken) {
        return new JwtResponse(accessToken);
    }
}
