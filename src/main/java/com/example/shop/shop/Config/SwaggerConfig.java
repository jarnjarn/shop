package com.example.shop.shop.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Value("${app.JWT_AUTH_HEADER}")
    private String JWT_AUTH_HEADER;

    @Bean
    public UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder().operationsSorter(OperationsSorter.METHOD).build();
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30).useDefaultResponseMessages(false)
                .select()
                // https://github.com/springfox/springfox/issues/631#issuecomment-330491674
                .apis(RequestHandlerSelectors.basePackage("org.springframework.boot").negate())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(List.of(apiKey()))
                .securityContexts(List.of(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Affiliate Marketing",
                "Affiliate Marketing APIs",
                "0.0.1",
                "/",
                new Contact("redshop", "https://redshop.com.vn", ""),
                "",
                "",
                Collections.emptyList());
    }

    private ApiKey apiKey() {
        return new ApiKey(JWT_AUTH_HEADER, "JWT", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(List.of(defaultSecurityReference()))
                .build();
    }

    private SecurityReference defaultSecurityReference() {
        var authScopes = new AuthorizationScope[1];
        authScopes[0] = new AuthorizationScope("global", "Access everything");
        return new SecurityReference(JWT_AUTH_HEADER, authScopes);
    }
}
