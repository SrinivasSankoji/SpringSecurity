package com.chary.bhaumik;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import lombok.val;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
	String swaggerToken ="";    

	@Bean
	public Docket productAPi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.forCodeGeneration(true)
				.globalOperationParameters(globalParameterList())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.chary.bhaumik.jwt.controller"))
				.paths(Predicates.not(PathSelectors.regex("/error.*")))
				.build()
				.groupName("JWTSwagger")
				.apiInfo(metaInfo());
				/**.genericModelSubstitutes(ResponseEntity.class)
				.ignoredParameterTypes(Pageable.class)
				.ignoredParameterTypes(java.sql.Date.class)
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class)
				.securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.newArrayList(apiKey()))
				.useDefaultResponseMessages(false);**/
		return docket;
	}
	
	private ApiInfo metaInfo() {

		Contact contact = new Contact("Srinivas.Sankoji", "https://youtube.com", "Srinivas.Sankoji@ril.com");
		return new ApiInfoBuilder().title("Rest API Documentation").description("Service Dependency").version("1.0")
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").contact(contact)
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
	}
	
	private List<Parameter> globalParameterList() {
		val authTokenHeader = new ParameterBuilder().name("AUTH-TOKEN").modelRef(new ModelRef("string")).required(true)
				.parameterType("header").description("Basic Auth Token").build();
		return Collections.singletonList(authTokenHeader);
	}

}
