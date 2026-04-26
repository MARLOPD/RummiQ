package com.rummyq.backend.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rummyq.backend.config.DatabaseConnection;
import com.rummyq.backend.models.RegistrationRequirements;

public class UserRepository {

    public void createUser(RegistrationRequirements req) throws Exception {

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (user_name, email, password_hash, created_at, last_login, status) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, req.userName);
            stmt.setString(2, req.email);
            stmt.setString(3, req.password);
            stmt.setTimestamp(4, req.createdAt);
            stmt.setTimestamp(5, req.lastLogin);
            stmt.setString(6, req.status);

            stmt.executeUpdate();
        } catch (Exception error) {
            throw new Exception("Error creating user: " + error.getMessage());
        }
    }

    public String getPasswordHash(String email) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT password_hash FROM users WHERE email = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                return results.getString("password_hash");
            }
            return null;
        } catch (Exception error) {
            throw new Exception("Error retrieving password hash: " + error.getMessage());
        }
    }

}
