package api.local.managebd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ManagebdApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagebdApplication.class, args);
	}

}
