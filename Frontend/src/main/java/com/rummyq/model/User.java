package com.rummyq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modelo que representa un usuario registrado en el sistema.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private static String email;
    private static String password;
    private static String securityQuestion;

    @JsonProperty("answer")
    private static String securityAnswer;

    @JsonProperty("userName")
    private static String name;

    public User() {
    }

    public User(String name, String email, String password, String securityAnswer) {
        User.name = name;
        User.email = email;
        User.password = password;
        User.securityAnswer = securityAnswer;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String c) {
        User.password = c;
    }

    public static String getSecurityQuestion() {
        return securityQuestion;
    }

    public static void setSecurityQuestion(String p) {
        User.securityQuestion = p;
    }

    public static String getSecurityAnswer() {
        return securityAnswer;
    }

    public static void setSecurityAnswer(String r) {
        User.securityAnswer = r;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    @Override
    public String toString() {
        return "User{email='" + email + "', password='" + password + "', securityQuestion='" + securityQuestion
                + "', securityAnswer='" + securityAnswer + "', name='" + name + "'}";
    }
}
