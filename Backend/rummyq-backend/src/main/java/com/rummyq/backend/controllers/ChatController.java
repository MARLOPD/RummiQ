package com.rummyq.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import java.util.List;

import com.rummyq.backend.models.Mensaje;
import com.rummyq.backend.models.MensajeDTO;
import com.rummyq.backend.services.ChatService;

@RestController
@RequestMapping("/api/game")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

   @PostMapping("/{gameId}/chat")
    public ResponseEntity<MensajeDTO> enviarMensaje(
        @PathVariable Long gameId,
        @RequestBody MensajeDTO request) {

    
    if (request.getPlayerId() == null || 
        request.getContenido() == null || 
        request.getContenido().isBlank()) {
        return ResponseEntity.badRequest().build();
    }
    Mensaje mensaje = chatService.enviarMensaje(gameId, request.getPlayerId(), request.getContenido());
    return ResponseEntity.ok(chatService.toDTO(mensaje));
}
    @GetMapping("/{gameId}/chat")
    public ResponseEntity<List<MensajeDTO>> obtenerMensajes(@PathVariable Long gameId) {
    List<MensajeDTO> mensajes = chatService.obtenerMensajes(gameId)
        .stream()
        .map(chatService::toDTO)
        .toList();
    return ResponseEntity.ok(mensajes);
}
}

