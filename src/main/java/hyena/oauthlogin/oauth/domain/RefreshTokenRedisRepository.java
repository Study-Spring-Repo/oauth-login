package hyena.oauthlogin.oauth.domain;

import hyena.oauthlogin.oauth.exception.OauthException;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

    default RefreshToken getByRefreshToken(String refreshToken) {
        return findById(refreshToken).orElseThrow(OauthException.RefreshToken::new);
    }
}
