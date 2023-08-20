package hyena.oauthlogin.oauth.presentation.response;

import hyena.oauthlogin.oauth.domain.RefreshToken;

public record RefreshTokenResponse(String refreshToken) {

    public static RefreshTokenResponse from(final RefreshToken refreshToken) {
        return new RefreshTokenResponse(refreshToken.getRefreshToken());
    }
}
