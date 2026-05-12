package com.rummyq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modelo que representa un usuario registrado en el sistema.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String email;
    private String password;
    private String securityQuestion;

    @JsonProperty("answer")
    private String securityAnswer;

    @JsonProperty("userName")
    private String name;

    public User() {
    }

    public User(String name, String email, String password, String securityAnswer) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.securityAnswer = securityAnswer;
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

    public void setPassword(String c) {
        this.password = c;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String p) {
        this.securityQuestion = p;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String r) {
        this.securityAnswer = r;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{email='" + email + "', password='" + password + "', securityQuestion='" + securityQuestion
                + "', securityAnswer='" + securityAnswer + "', name='" + name + "'}";
    }
}
