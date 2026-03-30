package com.rummyq.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rummyq.backend.models.RegistrationRequirements;
import com.rummyq.backend.services.UserLogin;

@RestController
@RequestMapping("/api")
public class LoginManagment {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginManagment.class);
    
    @PostMapping("/login")
    public String login(@RequestBody RegistrationRequirements req) throws Exception {
        UserLogin login = new UserLogin();
        login.LoginUser(req);
        logger.info("Login successful for user: " + req.email);
        return "Login successful";
    }
}
