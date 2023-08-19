package hyena.oauthlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class OauthLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthLoginApplication.class, args);
	}

}
