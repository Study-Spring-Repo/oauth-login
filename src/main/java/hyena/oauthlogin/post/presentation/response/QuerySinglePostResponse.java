package hyena.oauthlogin.post.presentation.response;

import hyena.oauthlogin.post.domain.Post;

public class QuerySinglePostResponse {

    private final Long postId;
    private final String title;
    private final String content;
    private final QueryPostMemberResponse memberProfile;

    private QuerySinglePostResponse(final Long postId,
                                   final String title,
                                   final String content,
                                   final QueryPostMemberResponse memberProfile
    ) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.memberProfile = memberProfile;
    }

    public static QuerySinglePostResponse from(final Post post) {
        return new QuerySinglePostResponse(
                post.getId(),
                post.getTitle(),
                post.getContents(),
                QueryPostMemberResponse.from(post.getMember())
        );
    }

    public Long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public QueryPostMemberResponse getMemberProfile() {
        return memberProfile;
    }
}
