package com.rummyq.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rummyq.backend.models.LoginRequirements;
import com.rummyq.backend.models.RegistrationRequirements;
import com.rummyq.backend.services.UserManagment;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public String signUp(@RequestBody RegistrationRequirements req) throws Exception {
        UserManagment signUp = new UserManagment();
        signUp.UserSignUp(req);
        logger.info("signup successful for user: " + req.email);
        return "signup successful";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequirements req) throws Exception {
        UserManagment logIn = new UserManagment();
        logIn.UserLogIn(req);
        logger.info("login successful for user: " + req.email);
        return "login successful";
    }
}
