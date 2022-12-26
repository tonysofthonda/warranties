package api.local.netcommerce;

import java.security.Security;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		Security.setProperty("crypto.policy", "unlimited");
		return application.sources(NetcommerceApplication.class);
	}

}
