package hyena.oauthlogin.post.application;

import hyena.oauthlogin.member.domain.Member;
import hyena.oauthlogin.post.application.request.PostCreateRequest;
import hyena.oauthlogin.post.application.request.PostUpdateRequest;
import hyena.oauthlogin.post.domain.Post;
import hyena.oauthlogin.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long createPost(final Member loginMember, final PostCreateRequest request) {
        final Post post = Post.builder()
                .title(request.title())
                .contents(request.contents())
                .member(loginMember)
                .build();

        return postRepository.save(post).getId();
    }

    public List<Post> readAll() {
        return postRepository.findAll();
    }

    @Transactional
    public Long updatePost(final Member loginMember, final Long postId, final PostUpdateRequest request) {
        final Post foundPost = postRepository.getByPostId(postId);
        if (!foundPost.isOwner(loginMember)) {
            throw new IllegalStateException("현재 게시글의 주인이 아니기에 수정할 수 없습니다.");
        }

        foundPost.updatePost(request.title(), request.contents());
        return foundPost.getId();
    }
}
