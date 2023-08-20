package hyena.oauthlogin.oauth.authcode;

import hyena.oauthlogin.oauth.OauthType;

public interface AuthCodeRequestUrlProvider {

    OauthType oauthType();

    String requestUrl();
}
