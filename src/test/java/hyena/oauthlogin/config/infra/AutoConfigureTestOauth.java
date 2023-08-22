package hyena.oauthlogin.config.infra;

import hyena.oauthlogin.config.infra.oauth.MockOauthTestConfig;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import(MockOauthTestConfig.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles
public @interface AutoConfigureTestOauth {

    @AliasFor(annotation = ActiveProfiles.class, attribute = "value")
    String[] value() default {"test"};
}
