package hyena.oauthlogin.member.application;

import hyena.oauthlogin.member.domain.Member;
import hyena.oauthlogin.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member readByMemberId(final Long memberId) {
        return memberRepository.findByMemberId(memberId);
    }
}
