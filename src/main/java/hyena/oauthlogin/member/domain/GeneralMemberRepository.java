package hyena.oauthlogin.member.domain;

public interface GeneralMemberRepository extends MemberRepository {

    default Member findByMemberId(final Long memberId) {
        return findById(memberId).orElseThrow(() -> new IllegalArgumentException("입력된 식별자값(id)으로 회원을 조회할 수 없습니다."));
    }
}
