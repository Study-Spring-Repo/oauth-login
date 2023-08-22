package hyena.oauthlogin.config.infra.jwt;

import hyena.oauthlogin.infra.jwt.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;

public abstract class JwtTestUtils {

    private static final JwtConfig jwtConfig = new MockJwtConfig();

    public static String modifyExpiration(final String token) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecretKey())
                .requireIssuer(jwtConfig.getIssuer())
                .build()
                .parseClaimsJws(token)
                .getBody();

        final Date newExpiration = new Date(claims.getIssuedAt().getTime() + Duration.ofSeconds(1).toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(jwtConfig.getSecretKey(), SignatureAlgorithm.HS256)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(claims.getIssuedAt())
                .setExpiration(newExpiration)
                .compact();
    }
}
