package hyena.oauthlogin.infra.oauth.client;

import hyena.oauthlogin.infra.oauth.OauthInformation;
import hyena.oauthlogin.infra.oauth.OauthType;
import hyena.oauthlogin.infra.oauth.exception.OauthException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class OauthClientComposite {

    private final Map<OauthType, OauthClient> clients;

    public OauthClientComposite(final Set<OauthClient> oauthClients) {
        this.clients = oauthClients.stream()
                .collect(toMap(OauthClient::oauthType, identity()));
    }

    public OauthInformation findOauthInformation(final OauthType oauthType, final String authCode) {
        return Optional.ofNullable(clients.get(oauthType))
                .orElseThrow(OauthException.OauthInformation::new)
                .oauthInformation(authCode);
    }
}
