package com.rummyq.backend.services;

import com.rummyq.backend.models.RegistrationRequirements;
import com.rummyq.backend.repositories.UserRepository;

public class UserLogin {

    public void LoginUser(RegistrationRequirements req) throws Exception {
        UserRepository userRepo = new UserRepository();
        userRepo.createUser(req);
    }

    
}
