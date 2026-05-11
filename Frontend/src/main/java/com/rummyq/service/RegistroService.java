package com.rummyq.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Servicio que se comunica con el Backend REST para registrar usuarios.
 * Patrón Service Layer: separa la lógica de red de la vista (PantallaRegistro).
 */
public class RegistroService {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final HttpClient cliente = HttpClient.newHttpClient();

    /**
     * Envía los datos del nuevo usuario al backend.
     * @return true si el registro fue exitoso.
     * @throws Exception si hay error de red o el servidor no responde.
     */
    public boolean registrarUsuario(String nombre, String correo,
                                    String passHash) throws Exception {

        // Escapamos comillas por si el nombre las contiene
        String jsonNombre = nombre.replace("\"", "\\\"");
        String jsonCorreo = correo.replace("\"", "\\\"");

        String json = String.format(
            "{\"userName\":\"%s\",\"email\":\"%s\",\"passwordHash\":\"%s\"}",
            jsonNombre, jsonCorreo, passHash
        );

        HttpRequest peticion = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/registro"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpResponse<String> respuesta = cliente.send(
            peticion, HttpResponse.BodyHandlers.ofString()
        );

        if (respuesta.statusCode() == 200) {
            return respuesta.body().equals("OK");
        }
        throw new Exception("Servidor respondió: " + respuesta.statusCode());
    }
}