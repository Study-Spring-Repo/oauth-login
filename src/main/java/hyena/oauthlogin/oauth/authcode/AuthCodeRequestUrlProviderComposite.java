package hyena.oauthlogin.oauth.authcode;

import hyena.oauthlogin.oauth.OauthType;
import hyena.oauthlogin.oauth.exception.OauthException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Profile("!test")
@Component
public class AuthCodeRequestUrlProviderComposite {

    private final Map<OauthType, AuthCodeRequestUrlProvider> providers;

    public AuthCodeRequestUrlProviderComposite(final Set<AuthCodeRequestUrlProvider> providers) {
        this.providers = providers.stream()
                .collect(toMap(AuthCodeRequestUrlProvider::oauthType, identity()));
    }

    public String findRequestUrl(final OauthType oauthType) {
        return Optional.ofNullable(providers.get(oauthType))
                .orElseThrow(OauthException.RequestUrl::new)
                .requestUrl();
    }
}
