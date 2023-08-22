package hyena.oauthlogin.post.presentation.response;

import hyena.oauthlogin.post.domain.Post;

import java.util.List;

public class QueryAllPostsResponse {

    private final List<QuerySinglePostResponse> data;

    private QueryAllPostsResponse(final List<QuerySinglePostResponse> data) {
        this.data = data;
    }

    public static QueryAllPostsResponse from(final List<Post> posts) {
        final List<QuerySinglePostResponse> querySinglePostResponses = posts.stream()
                .map(QuerySinglePostResponse::from)
                .toList();

        return new QueryAllPostsResponse(querySinglePostResponses);
    }

    public List<QuerySinglePostResponse> getData() {
        return data;
    }
}
