package hyena.oauthlogin.oauth.argumentresolver;

import hyena.oauthlogin.infra.jwt.JwtDecoder;
import hyena.oauthlogin.member.domain.Member;
import hyena.oauthlogin.oauth.domain.OauthMemberRepository;
import hyena.oauthlogin.oauth.exception.OauthException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BEARER = "Bearer ";
    private static final String EMPTY = "";

    private final OauthMemberRepository oauthMemberRepository;
    private final JwtDecoder jwtDecoder;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMemberPrincipal.class)
               && parameter.getParameter().getType().isAssignableFrom(Member.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory
    ) throws Exception {
        final String authorization = webRequest.getHeader(AUTHORIZATION);
        if (Objects.isNull(authorization)) {
            throw new OauthException.HeaderAuthorization();
        }
        if (!authorization.startsWith(BEARER)) {
            throw new OauthException.HeaderBearer();
        }

        final String bearer = authorization.replaceFirst(BEARER, EMPTY);
        final Claims claims = jwtDecoder.decode(bearer);
        final Long memberId = claims.get("memberId", Long.class);

        return oauthMemberRepository.getByMemberId(memberId);
    }
}
