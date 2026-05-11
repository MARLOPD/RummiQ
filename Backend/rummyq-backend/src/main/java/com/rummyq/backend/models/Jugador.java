package com.rummyq.backend.models;
import java.util.List;
import java.util.ArrayList;

public class Jugador {

    private String nombre;
    private List<Ficha> mano;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    public void agregarFicha(Ficha ficha) {
        mano.add(ficha);
    }
    public void descartarFicha(Ficha ficha) {
        mano.remove(ficha);
    }
    public int obtenerValorTotal() {
        int total = 0;
        for (Ficha f : mano) {
            if (f.isEsComodin()) {
                total += 13;
            } else {
                total += f.getNumero();
            }
        }
        return total;
    }

    public boolean gano() { return mano.isEmpty(); }

    // Getters
    public String getNombre() { return nombre; }
    public List<Ficha> getMano() { return mano; }
    public int cantidadFichas() { return mano.size(); }

    @Override
    public String toString() {
        return nombre + " (" + mano.size() + " fichas)";
    }
}