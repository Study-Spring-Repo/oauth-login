package hyena.oauthlogin.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "oauthId", nullable = false, length = 255)
    private String oauthId;

    @Builder
    protected Member(final String name, final String oauthId) {
        this(null, name, oauthId);
    }

    protected Member(final Long id, final String name, final String oauthId) {
        this.id = id;
        this.name = name;
        this.oauthId = oauthId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Member{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", oauthId='" + oauthId + '\'' +
               '}';
    }
}
