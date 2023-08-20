package hyena.oauthlogin.oauth.presentation;

import hyena.oauthlogin.oauth.OauthType;
import hyena.oauthlogin.oauth.application.OauthService;
import hyena.oauthlogin.oauth.application.TokenService;
import hyena.oauthlogin.oauth.presentation.response.JwtAndRefreshTokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.HttpStatus.FOUND;

@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OauthController {

    private final OauthService oauthService;

    private final TokenService tokenService;

    @GetMapping("/{oauthType}")
    public ResponseEntity<Void> redirectAuthCode(
            @PathVariable("oauthType") OauthType oauthType,
            HttpServletResponse response
    ) throws IOException {
        final String redirectUri = oauthService.readAuthCodeThenRedirect(oauthType);
        response.sendRedirect(redirectUri);

        return ResponseEntity.status(FOUND).build();
    }

    @GetMapping("/new/{oauthType}")
    public ResponseEntity<JwtAndRefreshTokenResponse> loginByAuthCode(
            @PathVariable("oauthType") OauthType oauthType,
            @RequestParam("code") String authCode

    ) {
        final JwtAndRefreshTokenResponse response = oauthService.createMember(oauthType, authCode);

        return ResponseEntity.ok(response);
    }
}
