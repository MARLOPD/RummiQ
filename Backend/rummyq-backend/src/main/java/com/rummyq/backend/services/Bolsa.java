package com.rummyq.backend.services;
import java.util.List;

import com.rummyq.backend.models.Ficha;
import com.rummyq.backend.models.Jugador;

import java.util.ArrayList;
import java.util.Collections;

public class Bolsa {

    private JugadorService jugadorService;
    private List<Ficha> fichas;

    public Bolsa() {
        fichas = new ArrayList<>();
        generarFichas();
    }

    private void generarFichas() {
        List<Ficha> rojas     = new ArrayList<>();
        List<Ficha> azules    = new ArrayList<>();
        List<Ficha> negras    = new ArrayList<>();
        List<Ficha> amarillas = new ArrayList<>();

        for (int serie = 0; serie < 2; serie++) {
            for (int numero = 1; numero <= 13; numero++) {
                rojas.add(new Ficha(numero, Ficha.Color.ROJO));
                azules.add(new Ficha(numero, Ficha.Color.AZUL));
                negras.add(new Ficha(numero, Ficha.Color.NEGRO));
                amarillas.add(new Ficha(numero, Ficha.Color.AMARILLO));
            }
        }

        // Mezclar cada color por separado
        Collections.shuffle(rojas);
        Collections.shuffle(azules);
        Collections.shuffle(negras);
        Collections.shuffle(amarillas);

        // Intercalar colores uno a uno
        int total = rojas.size();
        for (int i = 0; i < total; i++) {
            fichas.add(rojas.get(i));
            fichas.add(azules.get(i));
            fichas.add(negras.get(i));
            fichas.add(amarillas.get(i));
        }

        // Agregar comodines
        fichas.add(new Ficha(Ficha.Color.NEGRO));
        fichas.add(new Ficha(Ficha.Color.ROJO));

        // Shuffle final
        Collections.shuffle(fichas);
    }

    public void repartir(List<Jugador> jugadores) {
        
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < 14; i++) {
                jugadorService = new JugadorService(jugador);
                jugadorService.recibirFicha(fichas.remove(0));
            }
        }
    }

    // En Mazo
    public Ficha robarFicha() {
        if (fichas.isEmpty()) return null; // indica que no hay fichas
        return fichas.remove(0);
    }

    public int cantidadRestante() { return fichas.size(); }
    public boolean estaVacio() { return fichas.isEmpty(); }
}