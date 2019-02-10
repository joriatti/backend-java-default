package com.backend.swagger;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

public class SwaggerDocket extends Docket {

    private final String appPath;
    private final String restPath;
    private final String apiPackage;
    private final String version;
    private final ApiInfo apiInfo;
    private final boolean enableUI;

    public SwaggerDocket(String appPath, String restPath, String apiPackage, String version,
                         ApiInfo apiInfo, boolean enableUI) {
        super(DocumentationType.SWAGGER_2);
        this.appPath = appPath;
        this.restPath = restPath;
        this.apiPackage = apiPackage;
        this.version = version;
        this.apiInfo = apiInfo;
        this.enableUI = enableUI;
    }

    public SwaggerDocket(String appPath, String restPath, String apiPackage, String version, ApiInfo apiInfo) {
        this(appPath, restPath, apiPackage, version, apiInfo, true);
    }

    public Docket getDocket() {
        return this.groupName(this.apiInfo.getVersion()).select()
                .apis(RequestHandlerSelectors.basePackage(this.apiPackage + "." + this.version))
                .paths(PathSelectors.any()).build().apiInfo(this.apiInfo)
                .pathProvider(new SwaggerPathProvider(this.restPath + this.version, this.appPath))
                .useDefaultResponseMessages(false).securitySchemes(Arrays.asList(this.apiKey()))
                .securityContexts(Arrays.asList(this.securityContext())).enable(this.enableUI);
    }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Arrays.asList(new SecurityReference("apiKey", authorizationScopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(this.defaultAuth())
                .forPaths(PathSelectors.any()).build();
    }
}