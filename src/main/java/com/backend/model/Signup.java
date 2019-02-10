package com.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "APP_SIGNUP_REQUESTS",
        indexes = {
                @Index(name = "IDX_USRREG_EMAIL", columnList = "EMAIL"),
                @Index(name = "IDX_USRREG_HASH", columnList = "SIGNUP_HASH")
        }
)
public class Signup {

    @Id
    @Column(name = "ID", length = 36)
    private String id;

    @Column(name = "EMAIL", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100)
    private String lastName;

    @Column(name = "PASSWORD", length = 200, nullable = false)
    private String password;

    @Column(name = "RETYPE_PASSWORD", length = 200, nullable = false)
    private String retypePassword;

    @Column(name = "REGISTER_DATE", nullable = false)
    private LocalDateTime registerDate;

    @Column(name = "SIGNUP_HASH", length = 32, nullable = false)
    private String signupHash; // hash enviado por link no email

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

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public String getSignupHash() {
        return signupHash;
    }

    public void setSignupHash(String signupHash) {
        this.signupHash = signupHash;
    }
}