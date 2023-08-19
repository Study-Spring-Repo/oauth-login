package hyena.oauthlogin.config.converter;

import hyena.oauthlogin.infra.oauth.OauthType;
import org.springframework.core.convert.converter.Converter;

public class OauthTypeConverter implements Converter<String, OauthType> {

    @Override
    public OauthType convert(final String source) {
        return OauthType.from(source);
    }
}
