package com.sk.wiki.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.sk.wiki.services" })
public class ServiceConfig {

	public ServiceConfig() {

	}
}
