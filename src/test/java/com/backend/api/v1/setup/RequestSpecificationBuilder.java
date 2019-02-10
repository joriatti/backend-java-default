package com.backend.api.v1.setup;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class RequestSpecificationBuilder {
    private Map<String, String> headers = new HashMap();

    private RequestSpecificationBuilder(String token) {
        this.headers.put("Authorization", token);
    }

    private RequestSpecificationBuilder() {
    }

    public static RequestSpecificationBuilder requestWithLogin(String email, String password) {
        return new RequestSpecificationBuilder(getTokenForUserEmailAndPassword(email, password));
    }

    public static RequestSpecificationBuilder requestWithoutLogin() {
        return new RequestSpecificationBuilder();
    }

    public RequestSpecificationBuilder header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public RequestSpecification build() {
        return RestAssured.given().headers(this.headers);
    }

    public static String getTokenForUserEmailAndPassword(String email, String password) {
        class Login {
            public String email;
            public String password;
        }

        Login login = new Login();
        login.email = email;
        login.password = password;

        Response postLogin = RequestSpecificationBuilder.requestWithoutLogin().build()
                .header("Content-Type", "application/json").body(login)
                .post(AbstractTestController.LOGIN).andReturn();

        return postLogin.getBody().asString();
    }
}