package com.rummyq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rummyq.model.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RegistrationService {

    private static final String BASE_URL = "https://rummiqback.onrender.com/api";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RegistrationService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public boolean signUp(User user) {
        try {
            RegistrationRequest regReq = new RegistrationRequest(user);
            String json = objectMapper.writeValueAsString(regReq);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/signup"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return Boolean.parseBoolean(response.body());

        } catch (Exception e) {
            System.err.println("Error en el registro: " + e.getMessage());
            return false;
        }
    }

    // Clase interna para el JSON de registro
    private record RegistrationRequest(String userName, String email, String password, String answer, String status) {
        public RegistrationRequest(User user) {
            this(user.getName(), user.getEmail(), user.getPassword(), user.getSecurityAnswer(), "active");
        }
    }
}
