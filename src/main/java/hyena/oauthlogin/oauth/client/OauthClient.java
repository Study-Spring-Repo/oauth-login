package hyena.oauthlogin.oauth.client;

import hyena.oauthlogin.oauth.OauthInformation;
import hyena.oauthlogin.oauth.OauthType;

public interface OauthClient {

    OauthType oauthType();

    OauthInformation oauthInformation(final String authCode);
}
