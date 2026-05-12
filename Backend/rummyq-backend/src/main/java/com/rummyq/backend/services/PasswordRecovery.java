package com.rummyq.backend.services;

import com.rummyq.backend.config.BCryptEncryption;
import com.rummyq.backend.models.RegistrationRequirements;
import com.rummyq.backend.repositories.RecoveryPasswordRepository;

public class PasswordRecovery {
    public RegistrationRequirements findUserByEmail(String email) throws Exception {
        RecoveryPasswordRepository recoveryPasswordRepo = new RecoveryPasswordRepository();

        RegistrationRequirements user = recoveryPasswordRepo.findUserByEmail(email);

        return user;
    }

    public boolean verifyAnswer(String email, String answer) throws Exception {
        RecoveryPasswordRepository recoveryPasswordRepo = new RecoveryPasswordRepository();

        return recoveryPasswordRepo.verifyAnswer(email, answer);
    }

    public void updatePassword(String email, String password) throws Exception {
        RecoveryPasswordRepository recoveryPasswordRepo = new RecoveryPasswordRepository();
        password = BCryptEncryption.Encrypt(password);
        recoveryPasswordRepo.updatePassword(email, password);
    }
}
