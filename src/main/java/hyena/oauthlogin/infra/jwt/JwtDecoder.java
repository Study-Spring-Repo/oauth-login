package hyena.oauthlogin.infra.jwt;

import hyena.oauthlogin.infra.jwt.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtDecoder {

    private final JwtConfig jwtConfig;

    public Claims decode(final String token) {
        final JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecretKey())
                .requireIssuer(jwtConfig.getIssuer())
                .build();

        return jwtParser.parseClaimsJws(token).getBody();
    }
}
