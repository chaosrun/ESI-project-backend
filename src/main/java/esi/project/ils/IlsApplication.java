package esi.project.ils;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active:default}.properties")
public class IlsApplication {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(IlsApplication.class, args);
	}

}
