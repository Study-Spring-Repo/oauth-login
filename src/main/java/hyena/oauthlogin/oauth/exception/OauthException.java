package hyena.oauthlogin.oauth.exception;

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

    public static class RefreshToken extends OauthException {

        public RefreshToken() {
            super("RefreshToken 이 잘못되어 오류가 발생하였습니다.");
        }
    }

    public static class HeaderAuthorization extends OauthException {

        public HeaderAuthorization() {
            super("헤더에 Authorization 이 존재하지 않습니다.");
        }
    }

    public static class HeaderBearer extends OauthException {

        public HeaderBearer() {
            super("Authorization 이 Bearer 형식이 아닙니다.");
        }
    }
}
