package hyena.oauthlogin.oauth;

import static java.util.Locale.ENGLISH;

public enum OauthType {

    GITHUB;

    public static OauthType from(final String type) {
        return OauthType.valueOf(type.toUpperCase(ENGLISH));
    }
}
