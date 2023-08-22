package hyena.oauthlogin.member.domain;

import hyena.oauthlogin.config.RepositoryTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest extends RepositoryTestConfig {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원을_저장한_후_조회한다() {
        // given
        final Member member = Member.builder()
                .name("헤나")
                .build();

        // when
        final Member savedMember = memberRepository.saveAndFlush(member);
        final Member foundMember = memberRepository.findByMemberId(savedMember.getId());

        // then
        assertThat(foundMember).isEqualTo(member);
    }
}
