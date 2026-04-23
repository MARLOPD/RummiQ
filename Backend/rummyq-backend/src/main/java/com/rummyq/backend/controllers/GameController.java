package com.rummyq.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rummyq.backend.models.Partida;
import com.rummyq.backend.models.PartidaDTO;
import com.rummyq.backend.models.StartRequest;
import com.rummyq.backend.services.PartidaService;
import com.rummyq.backend.services.PartidaMapper;

@RestController
@RequestMapping("/api/game")
public class GameController {
   private final PartidaService partidaService;

    public GameController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping("/start")
    public ResponseEntity<PartidaDTO> iniciar(@RequestBody StartRequest request) {
        Partida partida = 
        partidaService.iniciarPartida(request.getRoomId(), request.getPlayers());
        return ResponseEntity.ok(PartidaMapper.toDTO(partida));
    }

}
