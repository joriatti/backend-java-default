package com.backend.api.v1.controller;

import com.backend.api.v1.dto.UserResponse;
import com.backend.api.v1.setup.AbstractTestController;
import com.backend.api.v1.setup.RequestSpecificationBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserControllerIT extends AbstractTestController {

    @Test
    public void findUserByIdTest() {
        Response get = RequestSpecificationBuilder
                .requestWithLogin(defaultAdminEmail, defaultAdminPassword).build()
                .header("Content-Type", "application/json")
                .get(USERS_RESOURCE + "/" + defaultAdminId).andReturn();

        String json = get.getBody().asString();
        UserResponse userResponse = new JsonPath(json).getObject("$", UserResponse.class);

        assertThat(userResponse.getId()).isNotEmpty();
        assertThat(userResponse.getId()).isEqualTo(defaultAdminId);
        assertThat(userResponse.getEmail()).isEqualTo(defaultAdminEmail);
    }

    @Test
    public void findUserByIdNotFoundTest() {
        String token = RequestSpecificationBuilder
                .getTokenForUserEmailAndPassword(defaultAdminEmail, defaultAdminPassword);

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .when().get(USERS_RESOURCE + "/" + "aaa")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .body("code", equalTo("USER_NOT_FOUND_FOR_ID"))
                .body("message", containsString("aaa"));
    }
}
