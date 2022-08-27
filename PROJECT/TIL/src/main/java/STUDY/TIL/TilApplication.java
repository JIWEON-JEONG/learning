package STUDY.TIL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class TilApplication {
	public static void main(String[] args) {
		SpringApplication.run(TilApplication.class, args);
	}
}
