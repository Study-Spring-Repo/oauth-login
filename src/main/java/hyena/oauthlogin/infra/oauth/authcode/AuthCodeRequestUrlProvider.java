package hyena.oauthlogin.infra.oauth.authcode;

import hyena.oauthlogin.infra.oauth.OauthType;

public interface AuthCodeRequestUrlProvider {

    OauthType oauthType();

    String requestUrl();
}
