package com.rummyq.model;

/**
 * Modelo que representa un usuario registrado en el sistema.
 */
public class User {

    private String email;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private String name;

    public User() {
    }

    public User(String email, String password,
            String securityQuestion, String securityAnswer) {
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.name = email.split("@")[0];
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
        return "User{email='" + email + "', name='" + name + "'}";
    }
}
