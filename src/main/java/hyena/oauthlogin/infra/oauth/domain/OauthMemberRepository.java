package hyena.oauthlogin.infra.oauth.domain;

import hyena.oauthlogin.member.domain.Member;
import hyena.oauthlogin.member.domain.MemberRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OauthMemberRepository extends MemberRepository {

    default Member findByMemberId(Long memberId) {
        return findById(memberId).orElseThrow(() -> new IllegalArgumentException("입력된 식별자값(id)으로 회원을 조회할 수 없습니다."));
    }

    Optional<Member> findByOauthId(@Param("oauthId") String oauthId);
}
