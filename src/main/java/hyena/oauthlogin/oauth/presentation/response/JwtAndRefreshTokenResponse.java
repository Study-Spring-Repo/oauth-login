package hyena.oauthlogin.oauth.presentation.response;

import hyena.oauthlogin.oauth.domain.RefreshToken;

public record JwtAndRefreshTokenResponse(String accessToken,
                                         String refreshToken
) {

    public static JwtAndRefreshTokenResponse of(final String accessToken, final RefreshToken refreshToken) {
        return new JwtAndRefreshTokenResponse(accessToken, refreshToken.getRefreshToken());
    }
}
