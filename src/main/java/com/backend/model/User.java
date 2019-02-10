package com.backend.model;

import com.backend.model.enums.UserStateEnum;
import com.backend.model.enums.UserTypeEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "APP_USER")
public class User {

    @Id
    @Column(name = "ID", length = 36)
    private String id;

    @Column(name = "EMAIL", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100)
    private String lastName;

    @Column(name = "PASSWORD", length = 200)
    private String password;

    @Column(name = "TENANT_ID", length = 36, nullable = false)
    private String tenantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATE", length = 30, nullable = false)
    private UserStateEnum state = UserStateEnum.INACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_TYPE", length = 30, nullable = false)
    private UserTypeEnum userType = UserTypeEnum.ADMIN;

    @Column(name = "IS_MAIN_USER")
    private boolean mainUser; // indica usuário que criou o Tenant

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public UserStateEnum getState() {
        return state;
    }

    public void setState(UserStateEnum state) {
        this.state = state;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    public boolean isMainUser() {
        return mainUser;
    }

    public void setMainUser(boolean mainUser) {
        this.mainUser = mainUser;
    }
}