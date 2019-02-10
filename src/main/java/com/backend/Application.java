package com.backend;

import com.backend.security.TokenAuthenticationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class Application {

    private static final String BACKEND_JWT_SIGNING_KEY = "backend.jwt.signing-key";

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        initialize(context);
    }

    private static void initialize(ApplicationContext context) {
        String jwtSecretSign = context.getEnvironment().getProperty(BACKEND_JWT_SIGNING_KEY);
        TokenAuthenticationService.setSecret(jwtSecretSign);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}