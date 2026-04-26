package com.rummyq.backend.models;

import java.sql.Timestamp;

public class RegistrationRequirements {
    public String userName;
    public String email;
    public String password;
    public String status;
    public Timestamp createdAt;
    public Timestamp lastLogin;
}