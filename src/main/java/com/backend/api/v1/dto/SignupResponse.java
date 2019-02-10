package com.backend.api.v1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

@ApiModel("SignupResponse")
public class SignupResponse {

    @ApiModelProperty(value = "User Register Id")
    private String id;

    @ApiModelProperty(value = "Register Date")
    private LocalDateTime registerDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }
}