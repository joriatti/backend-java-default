package com.backend.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASE_PATH_APP = "/";
    private static final String BASE_PATH_REST = "/api/";
    private static final String BASE_PACKAGE = "com.backend.api";

    @Value("${swagger-ui.enable:true}")
    private boolean enableUI;

    @Bean
    public Docket apiV1() {
        return getDocket("v1");
    }

    private Docket getDocket(String version) {
        return new SwaggerDocket(
                BASE_PATH_APP, BASE_PATH_REST, BASE_PACKAGE, version, apiInfo(version), enableUI).getDocket();
    }

    private ApiInfo apiInfo(String version) {
        return new ApiInfo("Backend", // title
                "Backend",
                version,
                null, // termsOfServiceUrl
                null, // contact
                null, // license
                null, // licenseUrl
                Collections.emptyList() // vendorExtensions
        );
    }
}