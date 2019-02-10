package com.backend.api.v1.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.backend.api.v1.dto.UserResponseList;
import com.backend.api.v1.setup.AbstractTestController;
import com.backend.api.v1.setup.RequestSpecificationBuilder;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TenantControllerIT extends AbstractTestController {
	
	@Test
    public void findAllUsersByTenantIdTest() {
    	
    	Response get = RequestSpecificationBuilder
                .requestWithLogin(defaultAdminEmail, defaultAdminPassword).build()
                .header("Content-Type", "application/json")
                .get(TENANTS_RESOURCE + "/" + defaultAdminTenantId + "/users").andReturn();

        String json = get.getBody().asString();
        UserResponseList list = new JsonPath(json).getObject("$", UserResponseList.class);
        
        assertThat(list.getItems()).hasSize(1);
        assertThat(list.getItems().get(0).getEmail()).isEqualTo(defaultAdminEmail);
    	
    }

}
