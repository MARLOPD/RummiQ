package com.rummyq.backend.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.rummyq.backend.config.DatabaseConnection;
import com.rummyq.backend.models.RegistrationRequirements;

public class UserRepository {

    public void createUser(RegistrationRequirements req) throws Exception {

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (user_name, email, password_hash, created_at, last_login, status) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, req.userName);
            stmt.setString(2, req.email);
            stmt.setString(3, req.passwordHash);
            stmt.setTimestamp(4, req.createdAt);
            stmt.setTimestamp(5, req.lastLogin);
            stmt.setString(6, req.status);

            stmt.executeUpdate();
        } catch (Exception error) {
            throw new Exception("Error creating user: " + error.getMessage());
        }
    }
}
