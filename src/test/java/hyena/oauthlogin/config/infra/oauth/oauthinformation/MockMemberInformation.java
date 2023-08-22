package hyena.oauthlogin.config.infra.oauth.oauthinformation;

import hyena.oauthlogin.oauth.OauthInformation;

public class MockMemberInformation implements OauthInformation {

    @Override
    public String accessToken() {
        return "member_test_access_token_member_test_access_token_member_test_access_token_";
    }

    @Override
    public String oauthId() {
        return "member_test_oauthId";
    }

    @Override
    public String name() {
        return "member_test_name";
    }
}
