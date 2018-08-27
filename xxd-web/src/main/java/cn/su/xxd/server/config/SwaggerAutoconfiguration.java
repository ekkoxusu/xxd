package cn.su.xxd.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Slf4j
@Configuration
@EnableSwagger2
@EnableConfigurationProperties({SwaggerProperties.class})
@ConditionalOnClass({ ApiInfo.class })
public class SwaggerAutoconfiguration {
	
	private final SwaggerProperties swaggerProperties;

	public SwaggerAutoconfiguration(SwaggerProperties swaggerProperties) {
		this.swaggerProperties = swaggerProperties;
	}

	@Bean
	public Docket swaggerSpringfoxApiDocket() {
		log.info("Starting Swagger");
		Contact contact = new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(),
				swaggerProperties.getContactEmail());

		ApiInfo apiInfo = new ApiInfo(swaggerProperties.getTitle(), swaggerProperties.getDescription(),
				swaggerProperties.getVersion(), swaggerProperties.getTermsOfServiceUrl(), contact,
				swaggerProperties.getLicense(), swaggerProperties.getLicenseUrl(), Collections.emptyList());

		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.apiInfo(apiInfo).forCodeGeneration(true).directModelSubstitute(java.nio.ByteBuffer.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class);
		docket = docket.select().paths(regex("/.*"))
				.apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage())).build();
		log.info("==>.Started Swagger in {} ms, config properties : {}");
		return docket;
	}
	

}
