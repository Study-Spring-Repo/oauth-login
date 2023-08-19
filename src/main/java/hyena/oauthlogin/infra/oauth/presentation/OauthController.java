package hyena.oauthlogin.infra.oauth.presentation;

import hyena.oauthlogin.infra.oauth.OauthType;
import hyena.oauthlogin.infra.oauth.application.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FOUND;

@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/{oauthType}")
    public ResponseEntity<Void> redirectAuthCode(
            @PathVariable("oauthType") OauthType oauthType,
            HttpServletResponse response
    ) throws IOException {
        final String redirectUri = oauthService.readAuthCodeThenRedirect(oauthType);
        response.sendRedirect(redirectUri);

        return ResponseEntity
                .status(FOUND)
                .build();
    }

    @GetMapping("/login/{oauthType}")
    public ResponseEntity<Void> login(
            @PathVariable("oauthType") OauthType oauthType,
            @RequestParam("code") String authCode
    ) {
        final String token = oauthService.createNewMemberOrLogin(oauthType, authCode);

        return ResponseEntity.ok()
                .header(AUTHORIZATION, token)
                .build();
    }
}
