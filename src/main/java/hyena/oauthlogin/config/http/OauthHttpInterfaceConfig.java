package hyena.oauthlogin.config.http;

import hyena.oauthlogin.oauth.github.http.GithubHttpInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class OauthHttpInterfaceConfig {

    @Bean
    public GithubHttpInterface githubHttpInterface() {
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(WebClient.create()))
                .build()
                .createClient(GithubHttpInterface.class);
    }
}
