package com.rummyq.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rummyq.model.User;

public class RecoveryPasswordService {
    private static final String BASE_URL = "https://rummiqback.onrender.com/api";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RecoveryPasswordService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public User findUserByEmail(String email) {
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/findUser"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(email))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 && response.body() != null) {
                return objectMapper.readValue(response.body(), User.class);
            }

            return null;

        } catch (Exception e) {
            System.err.println("Error en la conexión con el servidor: " + e.getMessage());
            return null;
        }
    }

    public boolean verifyAnswer(String email, String answer) {
        try {

            String json = objectMapper.writeValueAsString(new InnerRecoveryParams(email, answer, null));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/verifyAnswer"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 && response.body() != null) {
                return objectMapper.readValue(response.body(), Boolean.class);
            }

            return false;

        } catch (Exception e) {
            System.err.println("Error en la conexión con el servidor: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePassword(String email, String password) {
        try {

            String json = objectMapper.writeValueAsString(new InnerRecoveryParams(email, null, password));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/updatePassword"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 && response.body() != null) {
                return objectMapper.readValue(response.body(), Boolean.class);
            }

            return false;

        } catch (Exception e) {
            System.err.println("Error en la conexión con el servidor: " + e.getMessage());
            return false;
        }
    }

    private record InnerRecoveryParams(String email, String answer, String password) {
    }
}