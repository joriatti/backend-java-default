package com.backend.api.v1.controller;

import com.backend.api.v1.dto.SignupRequest;
import com.backend.api.v1.dto.SignupResponse;
import com.backend.api.v1.dto.UserResponse;
import com.backend.api.v1.setup.AbstractTestController;
import com.backend.api.v1.setup.RequestSpecificationBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class SignupControllerIT extends AbstractTestController {

    @Test
    public void testUserSignup() {

        SignupRequest request = new SignupRequest();
        request.setEmail("usertoregister@registeruser.com");
        request.setFirstName("User to Register");
        request.setLastName("User Last Name");
        request.setPassword("123");
        request.setRetypePassword("123");

        Response post = RequestSpecificationBuilder.requestWithoutLogin().build()
                .header("Content-Type", "application/json").body(request)
                .post(SIGNUP_RESOURCE + "/").andReturn();

        String json = post.getBody().asString();
        SignupResponse responsePost = new JsonPath(json).getObject("$", SignupResponse.class);

        Response get = RequestSpecificationBuilder.requestWithoutLogin().build()
                .get(SIGNUP_RESOURCE + "/" + responsePost.getId()).andReturn();

        String jsonGet = get.getBody().asString();
        SignupResponse responseGet = new JsonPath(jsonGet).getObject("$", SignupResponse.class);

        // Realiza a confirmação do usuário
        given().header("Content-Type", "application/json")
                .when()
                .post(SIGNUP_RESOURCE + "/usertoregister@registeruser.com/"
                        + getHashForSignupUser("usertoregister@registeruser.com"))
                .then()
                .statusCode(HttpStatus.CREATED.value());

        Response responseCurrentUserData = RequestSpecificationBuilder
                .requestWithLogin("usertoregister@registeruser.com", "123").build()
                .header("Content-Type", "application/json")
                .get(USERS_RESOURCE + "/current-user-data").andReturn();

        String jsonCurrentUserData = responseCurrentUserData.getBody().asString();
        UserResponse createdUser = new JsonPath(jsonCurrentUserData).getObject("$", UserResponse.class);

        assertThat(responsePost.getId()).isNotEmpty();
        assertThat(responsePost.getRegisterDate()).isNotNull();

        assertThat(responseGet.getId()).isNotEmpty();
        assertThat(responseGet.getRegisterDate()).isNotNull();

        assertThat(responsePost.getId()).isEqualTo(responseGet.getId());

        assertThat(createdUser.getEmail()).isEqualTo("usertoregister@registeruser.com");
        assertThat(createdUser.getFirstName()).isEqualTo("User to Register");
        assertThat(createdUser.getLastName()).isEqualTo("User Last Name");
        assertThat(createdUser.getState()).isEqualTo("ACTIVE");
        assertThat(createdUser.getUserType()).isEqualTo("ADMIN");
        assertThat(createdUser.getTenantId()).isNotNull();
    }

    @Test
    public void shouldValidateInvalidEmail() {

        SignupRequest request = new SignupRequest();
        request.setEmail("usertoregister@regis_teruser.com");
        request.setFirstName("User to Register");
        request.setPassword("123");
        request.setRetypePassword("123");

        given().header("Content-Type", "application/json").body(request).when().post(SIGNUP_RESOURCE + "/").then()
                .statusCode(HttpStatus.BAD_REQUEST.value()).and().body("code", equalTo("INVALID_EMAIL"));

    }

    @Test
    public void shouldValidateRequiredName() {

        SignupRequest request = new SignupRequest();
        request.setEmail("usertoregister@registeruser.com");
        request.setPassword("123");
        request.setRetypePassword("123");

        given().header("Content-Type", "application/json").body(request).when().post(SIGNUP_RESOURCE + "/").then()
                .statusCode(HttpStatus.BAD_REQUEST.value()).and().body("code", equalTo("NAME_REQUIRED"));

    }

    @Test
    public void shouldValidateRequiredPassword() {

        SignupRequest request = new SignupRequest();
        request.setEmail("usertoregister@registeruser.com");
        request.setFirstName("André");
        request.setRetypePassword("123");

        given().header("Content-Type", "application/json").body(request).when().post(SIGNUP_RESOURCE + "/").then()
                .statusCode(HttpStatus.BAD_REQUEST.value()).and().body("code", equalTo("PASSWORD_REQUIRED"));

    }

    @Test
    public void shouldValidatePasswordNotEqualsRetypePassword() {

        SignupRequest request = new SignupRequest();
        request.setEmail("usertoregister@registeruser.com");
        request.setFirstName("André");
        request.setPassword("1234");
        request.setRetypePassword("123");

        given().header("Content-Type", "application/json").body(request).when().post(SIGNUP_RESOURCE + "/").then()
                .statusCode(HttpStatus.BAD_REQUEST.value()).and().body("code", equalTo("PASSWORD_NOT_EQUALS_RETYPE_PASSWORD"));

    }

    @Test
    public void shouldThrowNotFoundException() {
        given().header("Content-Type", "application/json").when().get(SIGNUP_RESOURCE + "/" + "aaa")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("code", equalTo("RECORD_NOT_FOUND"));
    }

}