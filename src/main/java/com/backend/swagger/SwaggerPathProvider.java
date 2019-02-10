package com.backend.swagger;

import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.paths.Paths;

public class SwaggerPathProvider extends AbstractPathProvider {
    private final String apiPath;
    private final String appPath;

    public SwaggerPathProvider(String apiPath, String appPath) {
        this.apiPath = apiPath;
        this.appPath = appPath;
    }

    protected String applicationPath() {
        return this.appPath + this.apiPath;
    }

    protected String getDocumentationPath() {
        return "/";
    }

    public String getOperationPath(String operationPath) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/");
        return Paths.removeAdjacentForwardSlashes(uriComponentsBuilder
                .path(operationPath.replaceFirst(this.apiPath, "")).build().toString());
    }
}