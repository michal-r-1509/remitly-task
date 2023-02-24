package com.michal.remitlytaskapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
public class RemitlyTaskAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemitlyTaskAppApplication.class, args);
	}

	@Bean
	public Validator validator(){
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
