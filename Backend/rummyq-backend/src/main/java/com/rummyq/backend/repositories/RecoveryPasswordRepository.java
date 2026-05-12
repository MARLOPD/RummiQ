package com.rummyq.backend.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rummyq.backend.config.BCryptEncryption;
import com.rummyq.backend.config.DatabaseConnection;
import com.rummyq.backend.models.RegistrationRequirements;

public class RecoveryPasswordRepository {
    public RegistrationRequirements findUserByEmail(String email) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet results = stmt.executeQuery();

            if (results.next()) {
                RegistrationRequirements user = new RegistrationRequirements();
                user.userName = results.getString("user_name");
                user.email = results.getString("email");
                user.password = results.getString("password_hash");
                user.status = results.getString("status");
                user.answer = results.getString("answer");
                return user;
            }

            return null;
        } catch (Exception error) {
            throw new Exception("Error finding user by email: " + error.getMessage());
        }
    }

    public boolean verifyAnswer(String email, String answer) throws Exception {
        System.out.println("email: " + email);
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet results = stmt.executeQuery();

            if (!results.next()) {
                throw new Exception("User not found");
            }
            String hashAnswer = results.getString("answer");
            return BCryptEncryption.Verify(answer, hashAnswer);

        } catch (Exception error) {
            throw new Exception("Error verifying answer: " + error.getMessage());
        }
    }

    public void updatePassword(String email, String password) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE users SET password_hash = ? WHERE email = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, password);
            stmt.setString(2, email);

            stmt.executeUpdate();
        } catch (Exception error) {
            throw new Exception("Error updating password: " + error.getMessage());
        }
    }
}