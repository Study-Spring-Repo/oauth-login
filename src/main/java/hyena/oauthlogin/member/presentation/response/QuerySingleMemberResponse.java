package hyena.oauthlogin.member.presentation.response;

import hyena.oauthlogin.member.domain.Member;

public record QuerySingleMemberResponse(Long memberId,
                                        String name
) {

    public static QuerySingleMemberResponse from (final Member member) {
        return new QuerySingleMemberResponse(member.getId(), member.getName());
    }
}
