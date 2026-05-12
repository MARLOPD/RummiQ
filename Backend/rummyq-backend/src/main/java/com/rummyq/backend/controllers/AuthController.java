package com.rummyq.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rummyq.backend.models.LoginRequirements;
import com.rummyq.backend.models.RegistrationRequirements;
import com.rummyq.backend.services.PasswordRecovery;
import com.rummyq.backend.services.UserManagment;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public boolean signUp(@RequestBody RegistrationRequirements req) throws Exception {
        System.out.println("AuthController recibió - status: " + req.status + ", answer: " + req.answer);
        UserManagment signUp = new UserManagment();
        signUp.UserSignUp(req);
        logger.info("signup successful for user: " + req.email);
        return true;
    }

    @PostMapping("/login")
    public boolean login(@RequestBody LoginRequirements req) throws Exception {
        UserManagment logIn = new UserManagment();
        return logIn.UserLogIn(req);
    }

    @PostMapping("/findUser")
    public RegistrationRequirements findUserByEmail(@RequestBody String email) throws Exception {
        PasswordRecovery findUserByEmail = new PasswordRecovery();
        return findUserByEmail.findUserByEmail(email);
    }

    @PostMapping("/verifyAnswer")
    public boolean verifyAnswer(@RequestBody InnerAuthParams params) throws Exception {
        PasswordRecovery verifyAnswer = new PasswordRecovery();
        return verifyAnswer.verifyAnswer(params.email, params.answer);
    }

    @PostMapping("/updatePassword")
    public boolean updatePassword(@RequestBody InnerAuthParams params) throws Exception {
        PasswordRecovery updatePassword = new PasswordRecovery();
        updatePassword.updatePassword(params.email, params.password);
        return true;
    }

    private record InnerAuthParams(String email, String answer, String password) {
    }
}