package hyena.oauthlogin.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post getByPostId(Long postId) {
        return findById(postId).orElseThrow(() -> new IllegalArgumentException("입력된 식별자값(postId)로 게시글을 조회할 수 없습니다."));
    }
}
