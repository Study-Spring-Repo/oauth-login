package hyena.oauthlogin.infra.oauth.client;

import hyena.oauthlogin.infra.oauth.OauthInformation;
import hyena.oauthlogin.infra.oauth.OauthType;

public interface OauthClient {

    OauthType oauthType();

    OauthInformation oauthInformation(final String authCode);
}
