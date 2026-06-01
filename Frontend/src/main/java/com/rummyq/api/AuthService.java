package com.rummyq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rummyq.model.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AuthService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public boolean login(String email, String password) {
        try {
            LoginRequest loginReq = new LoginRequest(email, password);
            String json = objectMapper.writeValueAsString(loginReq);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            boolean validation = Boolean.parseBoolean(response.body());
            if (validation) {
                User.setEmail(email);
            }
            return validation;

        } catch (Exception e) {
            System.err.println("Error en la conexión con el servidor: " + e.getMessage());
            return false;
        }
    }

    private record LoginRequest(String email, String passwordText) {
    }
}
