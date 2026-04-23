package com.rummyq.backend.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.rummyq.backend.models.Mensaje;
import com.rummyq.backend.models.MensajeDTO;
import com.rummyq.backend.repositories.ChatRepository;
@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Mensaje enviarMensaje(Long gameId, Long playerId, String contenido) {
        Mensaje mensaje = new Mensaje();
        mensaje.setGameId(gameId);
        mensaje.setPlayerId(playerId);
        mensaje.setContenido(contenido);
        mensaje.setTimestamp(LocalDateTime.now());
        return chatRepository.save(mensaje);
    }

    public List<Mensaje> obtenerMensajes(Long gameId) {
        return chatRepository.findByGameId(gameId);
    }
    public MensajeDTO toDTO (Mensaje mensaje) {
    return new MensajeDTO(
        mensaje.getGameId(),
        mensaje.getPlayerId(),
        mensaje.getContenido(),
        mensaje.getTimestamp()
    );
}
}


