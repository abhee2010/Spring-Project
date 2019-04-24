package com.abhee.login;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
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
		// getActiverofileName(context);
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
    
	/*
	 * below two methods are used to redirect the 'http' local host traffic to more secure 'https'
	 * using the self signed certificate as if now  
	 */
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(redirectConnector());
		return tomcat;
	}

	private Connector redirectConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8081);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}

}
