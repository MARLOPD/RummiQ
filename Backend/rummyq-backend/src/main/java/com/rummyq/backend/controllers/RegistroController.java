package com.rummyq.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.rummyq.backend.models.RegistrationRequirements;
import com.rummyq.backend.services.UserLogin;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Controlador REST para el registro de nuevos usuarios.
 * Expone: POST /api/registro
 */
@RestController
@RequestMapping("/api")
public class RegistroController {

    private static final Logger logger = LoggerFactory.getLogger(RegistroController.class);

    @PostMapping("/registro")
    public String registro(@RequestBody RegistrationRequirements req) {
        try {
            // Completar campos que el frontend no envía
            req.status    = "activo";
            req.createdAt = Timestamp.from(Instant.now());
            req.lastLogin = Timestamp.from(Instant.now());

            UserLogin service = new UserLogin();
            service.LoginUser(req);

            logger.info("Usuario registrado: {}", req.email);
            return "OK";

        } catch (Exception e) {
            // Un error de clave duplicada significa correo ya registrado
            logger.warn("Fallo al registrar {}: {}", req.email, e.getMessage());
            return "ERROR:" + e.getMessage();
        }
    }
}
