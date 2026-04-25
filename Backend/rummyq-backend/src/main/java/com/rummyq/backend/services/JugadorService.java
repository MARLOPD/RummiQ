package com.rummyq.backend.services;
import com.rummyq.backend.models.Ficha;
import com.rummyq.backend.models.Jugador;


public class JugadorService {

    private Jugador jugador;

    public JugadorService(Jugador jugador) {
        this.jugador = jugador;
    }

    public void recibirFicha(Ficha ficha) {
        ficha.setPosicionEnMano(jugador.cantidadFichas());
        jugador.agregarFicha(ficha);
    }

    // En Jugador
    public boolean robarFicha(Bolsa bolsa) {
        Ficha ficha = bolsa.robarFicha();
        if (ficha == null) return false; 
        recibirFicha(ficha);
        return true; 
    }

    public void descartarFicha(Ficha ficha) {
        ficha.setEstaEnMesa(true);
        jugador.descartarFicha(ficha);
        for (int i = 0; i < jugador.getMano().size(); i++) {
            jugador.getMano().get(i).setPosicionEnMano(i);
        }
    }

    public boolean tieneFicha(Ficha ficha) {
        for (Ficha f : jugador.getMano()) {
            if (f.esIgual(ficha)) return true;
        }
        return false;
    }
   
}