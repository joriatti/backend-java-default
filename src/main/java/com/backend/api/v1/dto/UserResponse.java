package com.backend.api.v1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("UserResponse")
public class UserResponse {

    @ApiModelProperty(value = "User Id")
    private String id;

    @ApiModelProperty(value = "User Email")
    private String email;

    @ApiModelProperty(value = "User First Name")
    private String firstName;

    @ApiModelProperty(value = "User Last Name")
    private String lastName;

    @ApiModelProperty(value = "User Tenant Id")
    private String tenantId;

    @ApiModelProperty(value = "User Status")
    private String state;

    @ApiModelProperty(value = "User Type")
    private String userType;

    @ApiModelProperty(value = "Indicates if is the user that created the Tenant")
    private boolean mainUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isMainUser() {
        return mainUser;
    }

    public void setMainUser(boolean mainUser) {
        this.mainUser = mainUser;
    }
}
