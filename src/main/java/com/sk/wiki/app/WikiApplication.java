package com.sk.wiki.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.sk.wiki.config.PersistenceConfig;
import com.sk.wiki.config.SecurityConfig;
import com.sk.wiki.config.ServiceConfig;
import com.sk.wiki.config.WebConfig;

@SpringBootApplication
@Import({
		PersistenceConfig.class,
		ServiceConfig.class,
		SecurityConfig.class,
		WebConfig.class

})
public class WikiApplication {

	public static void main(final String[] args) {
		SpringApplication.run(WikiApplication.class, args);
	}
}
