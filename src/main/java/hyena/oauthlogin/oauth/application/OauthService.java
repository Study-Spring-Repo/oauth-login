package hyena.oauthlogin.oauth.application;

import hyena.oauthlogin.infra.jwt.JwtEncoder;
import hyena.oauthlogin.oauth.OauthInformation;
import hyena.oauthlogin.oauth.OauthType;
import hyena.oauthlogin.oauth.authcode.AuthCodeRequestUrlProviderComposite;
import hyena.oauthlogin.oauth.client.OauthClientComposite;
import hyena.oauthlogin.oauth.domain.OauthMemberRepository;
import hyena.oauthlogin.oauth.domain.RefreshToken;
import hyena.oauthlogin.oauth.domain.RefreshTokenRedisRepository;
import hyena.oauthlogin.oauth.presentation.response.JwtAndRefreshTokenResponse;
import hyena.oauthlogin.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;

    private final OauthClientComposite oauthClientComposite;

    private final OauthMemberRepository oauthMemberRepository;

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    private final JwtEncoder jwtEncoder;

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
}
