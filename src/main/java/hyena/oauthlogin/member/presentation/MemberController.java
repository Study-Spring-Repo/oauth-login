package hyena.oauthlogin.member.presentation;

import hyena.oauthlogin.member.domain.Member;
import hyena.oauthlogin.member.presentation.response.QuerySingleMemberResponse;
import hyena.oauthlogin.oauth.argumentresolver.AuthMemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    @GetMapping
    public ResponseEntity<QuerySingleMemberResponse> readByAccessToken(
            @AuthMemberPrincipal Member member
    ) {
        final QuerySingleMemberResponse response = QuerySingleMemberResponse.from(member);

        return ResponseEntity.ok(response);
    }
}
