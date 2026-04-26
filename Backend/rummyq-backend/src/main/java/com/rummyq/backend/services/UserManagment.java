package com.rummyq.backend.services;

import com.rummyq.backend.config.BCryptEncryption;
import com.rummyq.backend.models.LoginRequirements;
import com.rummyq.backend.models.RegistrationRequirements;
import com.rummyq.backend.repositories.UserRepository;

public class UserManagment {

    public void UserSignUp(RegistrationRequirements req) throws Exception {
        UserRepository userRepo = new UserRepository();
        req.password = BCryptEncryption.Encrypt(req.password);
        userRepo.createUser(req);
    }

    public void UserLogIn(LoginRequirements req) throws Exception {
        UserRepository userRepo = new UserRepository();
        String storedHash = userRepo.getPasswordHash(req.email);

        if (storedHash == null) {
            throw new Exception("Usuario no encontrado.");
        }

        if (!BCryptEncryption.Verify(req.passwordText, storedHash)) {
            throw new Exception("Contraseña incorrecta.");
        }
    }
}
