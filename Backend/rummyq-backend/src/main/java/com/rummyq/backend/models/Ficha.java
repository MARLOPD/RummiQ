package com.rummyq.backend.models;

import java.util.List;

public class Ficha {

    // Enum de colores
    public enum Color {
        RED, BLUE, BLACK, YELLOW
    }

    // Atributos
    private String numero;
    private Color color;
    private boolean esComodin;
    private boolean estaEnMesa;
    private int posicionEnMano;

    // Constructor ficha normal
    public Ficha(String numero, Color color) {
        this.numero = numero;
        this.color = color;
        this.esComodin = false;
        this.estaEnMesa = false;
        this.posicionEnMano = -1;
    }

    // Constructor comodín
    public Ficha(Color colorComodin) {
        this.numero = null;
        this.color = colorComodin;
        this.esComodin = true;
        this.estaEnMesa = false;
        this.posicionEnMano = -1;
    }

    // Getters
    public String getNumero() {
        return numero;
    }

    public Color getColor() {
        return color;
    }

    public boolean isEsComodin() {
        return esComodin;
    }

    public boolean isEstaEnMesa() {
        return estaEnMesa;
    }

    public int getPosicionEnMano() {
        return posicionEnMano;
    }

    // Setters
    public void setEstaEnMesa(boolean valor) {
        this.estaEnMesa = valor;
    }

    public void setPosicionEnMano(int pos) {
        this.posicionEnMano = pos;
    }

    // Lógica
    public boolean esIgual(Ficha otra) {
        if (this.esComodin && otra.esComodin)
            return this.color == otra.color;
        if (this.esComodin || otra.esComodin)
            return false;
        return this.numero.equals(otra.numero) && this.color == otra.color;
    }

    public boolean puedeUnirseA(List<Ficha> grupo) {
        if (esComodin)
            return true;
        if (grupo.isEmpty())
            return true;

        boolean mismoColor = true;
        boolean mismoNumero = true;

        for (Ficha f : grupo) {
            if (!f.isEsComodin()) {
                if (f.getColor() != this.color) {
                    mismoColor = false;
                }
                if (!f.getNumero().equals(this.numero)) {
                    mismoNumero = false;
                }
            }
        }

        return mismoColor || mismoNumero;
    }

    @Override
    public String toString() {
        return esComodin ? "Comodín-" + color : numero + "-" + color;
    }
}
