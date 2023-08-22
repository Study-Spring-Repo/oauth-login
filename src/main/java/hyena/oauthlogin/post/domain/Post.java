package hyena.oauthlogin.post.domain;

import hyena.oauthlogin.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table
public class Post {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "title", length = 500)
    private String title;

    @Column(name = "contents", columnDefinition = "TEXT")
    private String contents;

    @JoinColumn(name = "member_id", updatable = false, foreignKey = @ForeignKey(name = "post_to_member"))
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Builder
    protected Post(final String title, final String contents, final Member member) {
        this(null, title, contents, member);
    }

    protected Post(final Long id, final String title, final String contents, final Member member) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.member = member;
    }

    public void updatePost(final String title, final String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isOwner(final Member loginMember) {
        return this.member.equals(loginMember);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Post{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", contents='" + contents + '\'' +
               ", member=" + member.getId() +
               '}';
    }
}
