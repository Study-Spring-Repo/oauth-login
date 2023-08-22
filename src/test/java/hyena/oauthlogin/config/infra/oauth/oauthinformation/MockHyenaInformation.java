package hyena.oauthlogin.config.infra.oauth.oauthinformation;

import hyena.oauthlogin.oauth.OauthInformation;

public class MockHyenaInformation implements OauthInformation {

    @Override
    public String accessToken() {
        return "hyena_test_accessToken_hyena_test_accessToken_hyena_test_accessToken_hyena_test_accessToken";
    }

    @Override
    public String oauthId() {
        return "hyena_test_oauthId";
    }

    @Override
    public String name() {
        return "hyena_test_name";
    }
}
