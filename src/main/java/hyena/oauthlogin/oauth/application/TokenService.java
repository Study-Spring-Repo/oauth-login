package hyena.oauthlogin.oauth.application;

import hyena.oauthlogin.infra.jwt.JwtEncoder;
import hyena.oauthlogin.oauth.application.request.AccessTokenCreateRequest;
import hyena.oauthlogin.oauth.application.request.RefreshTokenCreateRequest;
import hyena.oauthlogin.oauth.domain.OauthMemberRepository;
import hyena.oauthlogin.oauth.domain.RefreshToken;
import hyena.oauthlogin.oauth.domain.RefreshTokenRedisRepository;
import hyena.oauthlogin.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TokenService {

    private final OauthMemberRepository oauthMemberRepository;

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    private final JwtEncoder jwtEncoder;

    @Transactional
    public RefreshToken createRefreshToken(final RefreshTokenCreateRequest request) {
        final Member foundMember = oauthMemberRepository.getByMemberId(request.memberId());
        final RefreshToken refreshToken = RefreshToken.newInstance(foundMember.getId());

        return refreshTokenRedisRepository.save(refreshToken);
    }

    @Transactional
    public String createJsonWebToken(final AccessTokenCreateRequest request) {
        final RefreshToken foundRefreshToken = refreshTokenRedisRepository.getByRefreshToken(request.refreshToken());

        return jwtEncoder.encode(Map.of("memberId", foundRefreshToken.getMemberId()));
    }
}
