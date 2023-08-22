package hyena.oauthlogin.oauth.presentation.response;

public record RefreshTokenResponse(String refreshToken) {

    public static RefreshTokenResponse from(final String refreshToken) {
        return new RefreshTokenResponse(refreshToken);
    }
}
