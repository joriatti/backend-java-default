package com.backend.api.v1.setup;

import com.backend.api.v1.dto.SignupRequest;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@ContextConfiguration(classes = {IntegrationTestConfiguration.class})
public class AbstractTestController {

    public static String API_V1;
    public static String USERS_RESOURCE;
    public static String SIGNUP_RESOURCE;
    public static String LOGIN;
    public static String TENANTS_RESOURCE;

    @LocalServerPort
    protected int randomPort;

    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected final String defaultAdminEmail = "adm@adm.com.br";
    protected final String defaultAdminPassword = "123456";
    protected String defaultAdminId;
    protected String defaultAdminTenantId;

    @Before
    public void setUp() {
        RestAssured.port = randomPort;
        RestAssured.baseURI = "http://localhost:";
        API_V1 = baseURI + port + "/api/v1/";
        USERS_RESOURCE = API_V1 + "users";
        SIGNUP_RESOURCE = API_V1 + "signup";
        LOGIN = API_V1 + "login";
        TENANTS_RESOURCE = API_V1 + "tenants";

        createDefaultAdminUser();
    }

    private void createDefaultAdminUser() {
        boolean notFound = false;
        try {
            getUserIdByEmail(defaultAdminEmail);
        } catch (Exception e) {
            notFound = true;
        }
        if (notFound) {
            SignupRequest request = new SignupRequest();
            request.setEmail(defaultAdminEmail);
            request.setFirstName("Default Admin User");
            request.setPassword(defaultAdminPassword);
            request.setRetypePassword(defaultAdminPassword);

            RequestSpecificationBuilder.requestWithoutLogin().build()
                    .header("Content-Type", "application/json").body(request)
                    .post(SIGNUP_RESOURCE + "/").andReturn();

            // Realiza a confirmação do usuário
            given().header("Content-Type", "application/json")
                    .when()
                    .post(SIGNUP_RESOURCE + "/" + defaultAdminEmail + "/" + getHashForSignupUser(defaultAdminEmail))
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }
        defaultAdminId = getUserIdByEmail(defaultAdminEmail);
        defaultAdminTenantId = getUserTenantIdByEmail(defaultAdminEmail);
    }

    protected String getHashForSignupUser(String email) {
        String sql = "SELECT SIGNUP_HASH FROM APP_SIGNUP_REQUESTS WHERE EMAIL = '" + email + "'";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    protected String getUserIdByEmail(String email) {
        String sql = "SELECT ID FROM APP_USER WHERE EMAIL = '" + email + "'";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
    
    protected String getUserTenantIdByEmail(String email) {
        String sql = "SELECT TENANT_ID FROM APP_USER WHERE EMAIL = '" + email + "'";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

}