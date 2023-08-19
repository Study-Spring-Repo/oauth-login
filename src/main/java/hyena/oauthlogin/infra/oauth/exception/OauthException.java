package hyena.oauthlogin.infra.oauth.exception;

public abstract class OauthException extends RuntimeException {

    OauthException(final String message) {
        super(message);
    }

    public static class RequestUrl extends OauthException {

        public RequestUrl() {
            super("Oauth 에서 RequestUrl 를 찾지 못하는 오류가 발생하였습니다.");
        }
    }

    public static class OauthInformation extends OauthException {


        public OauthInformation() {
            super("Oauth 에서 OauthInformation 를 받지 못하는 오류가 발생하였습니다.");
        }
    }
}
