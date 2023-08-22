package hyena.oauthlogin.post.presentation.response;

import hyena.oauthlogin.member.domain.Member;

public class QueryPostMemberResponse {

    private final Long memberId;
    private final String name;

    private QueryPostMemberResponse(final Long memberId, final String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public static QueryPostMemberResponse from(final Member member) {
        return new QueryPostMemberResponse(member.getId(), member.getName());
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }
}
