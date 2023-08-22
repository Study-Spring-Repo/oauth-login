package hyena.oauthlogin.post.presentation;

import hyena.oauthlogin.member.domain.Member;
import hyena.oauthlogin.oauth.argumentresolver.AuthMemberPrincipal;
import hyena.oauthlogin.post.application.PostService;
import hyena.oauthlogin.post.application.request.PostCreateRequest;
import hyena.oauthlogin.post.application.request.PostUpdateRequest;
import hyena.oauthlogin.post.presentation.response.QueryAllPostsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(
            @AuthMemberPrincipal Member loginMember,
            @RequestBody PostCreateRequest request
    ) {
        final Long savedPostId = postService.createPost(loginMember, request);

        final URI redirectUri = URI.create("/posts/" + savedPostId);

        return ResponseEntity.created(redirectUri).build();
    }

    @GetMapping
    public ResponseEntity<QueryAllPostsResponse> readAllPost() {
        final QueryAllPostsResponse response = QueryAllPostsResponse.from(postService.readAll());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
            @AuthMemberPrincipal Member loginMember,
            @PathVariable("postId") Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        final Long updatedId = postService.updatePost(loginMember, postId, request);

        final URI redirectUri = URI.create("/posts/" + updatedId);

        return ResponseEntity.noContent()
                .location(redirectUri)
                .build();
    }
}
