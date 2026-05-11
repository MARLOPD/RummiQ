package com.rummyq.backend.repositories;

import com.rummyq.backend.models.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRepository extends JpaRepository<Mensaje, Long> {
    // Método para obtener todos los mensajes de una partida
    List<Mensaje> findByGameId(Long gameId);
}
