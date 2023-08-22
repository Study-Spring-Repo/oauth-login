package hyena.oauthlogin.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member findByMemberId(final Long memberId) {
        return findById(memberId).orElseThrow(() -> new IllegalArgumentException("입력된 식별자값(id)으로 회원을 조회할 수 없습니다."));
    }
}
