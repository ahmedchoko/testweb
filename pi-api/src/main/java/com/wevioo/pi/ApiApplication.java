package com.wevioo.pi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

/**
 * Main Application
 */
@SpringBootApplication
@Configuration
public class ApiApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ApiApplication.class);
	}


}


