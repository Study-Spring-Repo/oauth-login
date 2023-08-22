package hyena.oauthlogin.config;

import hyena.oauthlogin.config.infra.AutoConfigureTestOauth;
import hyena.oauthlogin.oauth.domain.RefreshTokenRedisRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestOauth
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class RestAssuredTestConfig {

    @Autowired
    protected RefreshTokenRedisRepository refreshTokenRedisRepository;

    @BeforeEach
    void restAssuredSetUp(@LocalServerPort int port) {
        RestAssured.port = port;
    }
}
