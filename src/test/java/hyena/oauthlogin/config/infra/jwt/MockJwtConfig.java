package hyena.oauthlogin.config.infra.jwt;

import hyena.oauthlogin.infra.jwt.config.JwtConfig;

public class MockJwtConfig extends JwtConfig {

    private static final String SECRET_KEY = "test_secret_key_test_secret_key_test_secret_key_test_secret_key_test_secret_key_test_secret_key";
    private static final String ISSUER = "test";

    public MockJwtConfig() {
        super(SECRET_KEY, ISSUER);
    }
}
