package hyena.oauthlogin.oauth.application;

import hyena.oauthlogin.infra.jwt.JwtDecoder;
import hyena.oauthlogin.infra.jwt.JwtEncoder;
import hyena.oauthlogin.member.domain.Member;
import hyena.oauthlogin.oauth.OauthInformation;
import hyena.oauthlogin.oauth.OauthType;
import hyena.oauthlogin.oauth.application.request.AccessTokenCreateRequest;
import hyena.oauthlogin.oauth.application.request.RefreshTokenCreateRequest;
import hyena.oauthlogin.oauth.authcode.AuthCodeRequestUrlProviderComposite;
import hyena.oauthlogin.oauth.client.OauthClientComposite;
import hyena.oauthlogin.oauth.domain.OauthMemberRepository;
import hyena.oauthlogin.oauth.domain.RefreshToken;
import hyena.oauthlogin.oauth.domain.RefreshTokenRedisRepository;
import hyena.oauthlogin.oauth.presentation.response.JwtAndRefreshTokenResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Profile("!test")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;

    private final OauthClientComposite oauthClientComposite;

    private final OauthMemberRepository oauthMemberRepository;

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    public String readAuthCodeThenRedirect(final OauthType oauthType) {
        return authCodeRequestUrlProviderComposite.findRequestUrl(oauthType);
    }

    @Transactional
    public JwtAndRefreshTokenResponse createMember(final OauthType oauthType, final String authCode) {
        final OauthInformation oauthInformation = oauthClientComposite.findOauthInformation(oauthType, authCode);

        final Member member = Member.builder()
                .name(oauthInformation.name())
                .oauthId(oauthInformation.oauthId())
                .build();
        final Member savedMember = oauthMemberRepository.save(member);
        final String accessToken = encodeJwt(savedMember);

        final RefreshToken refreshToken = RefreshToken.newInstance(savedMember.getId());
        final RefreshToken savedRefreshToken = refreshTokenRedisRepository.save(refreshToken);

        return JwtAndRefreshTokenResponse.of(accessToken, savedRefreshToken);
    }

    private String encodeJwt(final Member savedMember) {
        return jwtEncoder.encode(Map.of(
                "memberId", savedMember.getId()
        ));
    }

    @Transactional
    public String createRefreshToken(final RefreshTokenCreateRequest request) {
        final Member foundMember = oauthMemberRepository.getByMemberId(request.memberId());
        final RefreshToken refreshToken = RefreshToken.newInstance(foundMember.getId());
        final RefreshToken savedRefreshToken = refreshTokenRedisRepository.save(refreshToken);

        return jwtEncoder.encode(Map.of("refreshToken", savedRefreshToken.getRefreshToken()));
    }

    public String createJsonWebToken(final AccessTokenCreateRequest request) {
        final Claims claims = jwtDecoder.decode(request.refreshToken());
        final String refreshToken = claims.get("refreshToken", String.class);
        final RefreshToken foundRefreshToken = refreshTokenRedisRepository.getByRefreshToken(refreshToken);

        return jwtEncoder.encode(Map.of("memberId", foundRefreshToken.getMemberId()));
    }
}
