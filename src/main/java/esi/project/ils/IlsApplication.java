package esi.project.ils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active:default}.properties")
public class IlsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IlsApplication.class, args);
	}

}
