package hyena.oauthlogin.acceptance;

import hyena.oauthlogin.config.RestAssuredTestConfig;
import hyena.oauthlogin.config.infra.jwt.JwtTestUtils;
import hyena.oauthlogin.member.presentation.response.QuerySingleMemberResponse;
import hyena.oauthlogin.oauth.application.request.AccessTokenCreateRequest;
import hyena.oauthlogin.oauth.domain.RefreshToken;
import hyena.oauthlogin.oauth.presentation.response.JwtAndRefreshTokenResponse;
import hyena.oauthlogin.oauth.presentation.response.JwtResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Test;

import static hyena.oauthlogin.oauth.OauthType.GITHUB;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class LoginAcceptanceTest extends RestAssuredTestConfig {

    @Test
    void AccessToken의_유효_기간이_지났을_때_RefreshToken을_이용해서_새로운_AccessToken을_재발급_받는다() throws Exception {
        Oauth서버에_로그인을_요청을_성공한다();
        var 액세스_토큰과_리프래시_토큰 = AuthCode로_회원가입_혹은_로그인을_진행을_성공한다();
        final var 액세스_토큰 = 액세스_토큰과_리프래시_토큰.accessToken();
        액세스_토큰과_리프래시_토큰.refreshToken();
        액세스_토큰으로_본인_회원_정보_조회를_성공한다(액세스_토큰);
    }

    @Test
    void AccessToken의_유효_기간이_지났을_때_RefreshToken을_이용해서_새로운_AccessToken을_재발급_받는다2() throws Exception {
        Oauth서버에_로그인을_요청을_성공한다();
        var 액세스_토큰과_리프래시_토큰_응답 = AuthCode로_회원가입_혹은_로그인을_진행을_성공한다();
        final var 액세스_토큰 = 액세스_토큰과_리프래시_토큰_응답.accessToken();
        final var 리프래시_토큰 = 액세스_토큰과_리프래시_토큰_응답.refreshToken();
        var 회원_단건_조회_응답 = 액세스_토큰으로_본인_회원_정보_조회를_성공한다(액세스_토큰);

        만료되지_않은_액세스_토큰이_존재할_경우_예외가_발생한다(액세스_토큰, 리프래시_토큰);

        var 만료된_액세스_토큰 = 액세스_토큰_유효_기간을_1초_후로_변경한다(액세스_토큰과_리프래시_토큰_응답);
        만료된_액세스_토큰으로_본인_회원_정보를_조회할_경우_예외가_발생한다(만료된_액세스_토큰);

        var 재발급된_액세스_토큰_응답 = 만료된_액세스_토큰을_리프래시_토큰을_이용해서_재발급에_성공한다(리프래시_토큰, 만료된_액세스_토큰);
        액세스_토큰으로_본인_회원_정보_조회를_성공한다(액세스_토큰);


        final RefreshToken 조회된_리프래시_토큰 = refreshTokenRedisRepository.getByRefreshToken(리프래시_토큰);
        assertSoftly(softly -> {
            softly.assertThat(조회된_리프래시_토큰).isNotNull();
            softly.assertThat(조회된_리프래시_토큰.getMemberId()).isEqualTo(회원_단건_조회_응답.memberId());
            softly.assertThat(조회된_리프래시_토큰.getRefreshToken()).isEqualTo(리프래시_토큰);
        });

    }

    private static void 만료되지_않은_액세스_토큰이_존재할_경우_예외가_발생한다(final String 액세스_토큰, final String 리프래시_토큰) {
        RestAssured
                .given()
                .auth().preemptive().oauth2(액세스_토큰)
                .contentType(APPLICATION_JSON_VALUE)
                .body(new AccessTokenCreateRequest(리프래시_토큰))
                .log().all()

                .when()
                .post("/oauth/access-token")

                .then()
                .log().all()
                .assertThat().statusCode(INTERNAL_SERVER_ERROR.value());
    }

    private static void 만료된_액세스_토큰으로_본인_회원_정보를_조회할_경우_예외가_발생한다(final String 만료된_액세스_토큰) {
        RestAssured
                .given()
                .auth().preemptive().oauth2(만료된_액세스_토큰)
                .log().all()

                .when()
                .get("/members")

                .then()
                .log().all()
                .assertThat().statusCode(INTERNAL_SERVER_ERROR.value());
    }

    private static String 액세스_토큰_유효_기간을_1초_후로_변경한다(final JwtAndRefreshTokenResponse jwtAndRefreshTokenResponse) throws InterruptedException {
        final String modifiedExpirationToken = JwtTestUtils.modifyExpiration(jwtAndRefreshTokenResponse.accessToken());
        Thread.sleep(1000);

        return modifiedExpirationToken;
    }

    JwtAndRefreshTokenResponse AuthCode로_회원가입_혹은_로그인을_진행을_성공한다() {
        JwtAndRefreshTokenResponse 액세스_토큰과_리프래시_토큰 = RestAssured
                .given()
                .pathParam("oauthType", GITHUB)
                .queryParam("code", "hyena_auth_code")
                .log().all()

                .when()
                .get("/oauth/new/{oauthType}")

                .then()
                .log().all()
                .assertThat().statusCode(OK.value())
                .extract().as(new TypeRef<>() {
                });

        assertSoftly(softly -> {
            softly.assertThat(액세스_토큰과_리프래시_토큰).isNotNull();
            softly.assertThat(액세스_토큰과_리프래시_토큰.accessToken()).isNotBlank();
            softly.assertThat(액세스_토큰과_리프래시_토큰.refreshToken()).isNotBlank();
        });

        return 액세스_토큰과_리프래시_토큰;
    }

    void Oauth서버에_로그인을_요청을_성공한다() {
        RestAssured
                .given()
                .pathParam("oauthType", GITHUB)
                .log().all()

                .when()
                .redirects().follow(false)
                .get("/oauth/{oauthType}")

                .then()
                .log().all()
                .assertThat().statusCode(FOUND.value());
    }

    QuerySingleMemberResponse 액세스_토큰으로_본인_회원_정보_조회를_성공한다(final String 액세스_토큰) {
        QuerySingleMemberResponse 회원_단건_조회_응답 = RestAssured
                .given()
                .auth().preemptive().oauth2(액세스_토큰)
                .log().all()

                .when()
                .get("/members")

                .then()
                .log().all()
                .assertThat().statusCode(OK.value())
                .extract().as(new TypeRef<>() {
                });

        assertSoftly(softly -> {
            softly.assertThat(회원_단건_조회_응답).isNotNull();
            softly.assertThat(회원_단건_조회_응답.memberId()).isNotNull();
            softly.assertThat(회원_단건_조회_응답.name()).isNotBlank();
        });

        return 회원_단건_조회_응답;
    }

    JwtResponse 만료된_액세스_토큰을_리프래시_토큰을_이용해서_재발급에_성공한다(
            final String 리프래시_토큰,
            final String 만료된_액세스_토큰
    ) {
        final JwtResponse 재발급된_액세스_토큰 = RestAssured
                .given()
                .auth().preemptive().oauth2(만료된_액세스_토큰)
                .contentType(APPLICATION_JSON_VALUE)
                .body(new AccessTokenCreateRequest(리프래시_토큰))
                .log().all()

                .when()
                .post("/oauth/access-token")

                .then()
                .log().all()
                .assertThat().statusCode(OK.value())
                .extract().as(new TypeRef<>() {
                });

        assertSoftly(softly -> {
            softly.assertThat(재발급된_액세스_토큰).isNotNull();
            softly.assertThat(재발급된_액세스_토큰.accessToken()).isNotBlank();
            softly.assertThat(재발급된_액세스_토큰.accessToken()).isNotEqualTo(만료된_액세스_토큰);
            softly.assertThat(재발급된_액세스_토큰.accessToken()).isNotEqualTo(리프래시_토큰);
        });

        return 재발급된_액세스_토큰;
    }

}
