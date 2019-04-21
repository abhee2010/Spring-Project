package com.abhee.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class LoginApplication extends SpringBootServletInitializer {
	
	@Override
	   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	      return application.sources(LoginApplication.class);
	   }
	 

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(LoginApplication.class, args);
		getActiverofileName(context);	
	}
	
	
	private static void getActiverofileName(ConfigurableApplicationContext context) {
		for (String name : context.getBeanDefinitionNames()) {
			System.out.println(name);
		}
	}
	@Profile("dev")
	@Bean
	public String qaBean() {
		return "dev";
	}

	@Profile("prod")
	@Bean
	public String prodBean() {
		return "prod";
	}


}
