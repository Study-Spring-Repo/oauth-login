package hyena.oauthlogin.infra.oauth.application;

import hyena.oauthlogin.infra.oauth.OauthInformation;
import hyena.oauthlogin.infra.oauth.OauthType;
import hyena.oauthlogin.infra.oauth.authcode.AuthCodeRequestUrlProviderComposite;
import hyena.oauthlogin.infra.oauth.client.OauthClientComposite;
import hyena.oauthlogin.infra.oauth.domain.OauthMemberRepository;
import hyena.oauthlogin.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;

    private final OauthClientComposite oauthClientComposite;

    private final OauthMemberRepository oauthMemberRepository;

    public String readAuthCodeThenRedirect(final OauthType oauthType) {
        return authCodeRequestUrlProviderComposite.findRequestUrl(oauthType);
    }

    @Transactional
    public String createNewMemberOrLogin(final OauthType oauthType, final String authCode) {
        final OauthInformation oauthInformation = oauthClientComposite.findOauthInformation(oauthType, authCode);

        final String oauthId = oauthInformation.oauthId();
        final Optional<Member> maybeMember = oauthMemberRepository.findByOauthId(oauthId);
        if (maybeMember.isEmpty()) {
            final Member member = Member.builder()
                    .name(oauthInformation.name())
                    .oauthId(oauthInformation.oauthId())
                    .build();

            oauthMemberRepository.save(member);
        }

        return oauthInformation.accessToken();
    }
}
