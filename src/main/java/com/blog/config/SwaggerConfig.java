package com.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER="Authorization";

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private List<SecurityContext> contexts(){
		return Arrays.asList(SecurityContext.builder().securityReferences(reference()).build());
	}




	private List<SecurityReference> reference() {

		AuthorizationScope scope=new AuthorizationScope("global", "accessEverything");

		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}));
	}

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getinfo()).
				securityContexts(contexts()).
				securitySchemes(Arrays.asList(apiKey())).
				select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	private ApiInfo getinfo() {

		return new ApiInfo("Blogging App(Backend)", "This project is developed by Mohammed Ruman",
				"1.0", "Terms of Service", new Contact("Mohammed Ruman", "to be updated", "mohammedruman25@gmail.com"), "Open source", "Opensource",Collections.EMPTY_LIST);
	}
}
