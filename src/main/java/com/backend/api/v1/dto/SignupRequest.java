package com.backend.api.v1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("SignupRequest")
public class SignupRequest {

    @ApiModelProperty(value = "User First Name")
    private String firstName;

    @ApiModelProperty(value = "User Last Name")
    private String lastName;

    @ApiModelProperty(value = "User Email")
    private String email;

    @ApiModelProperty(value = "Password")
    private String password;

    @ApiModelProperty(value = "Retype Password")
    private String retypePassword;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }
}