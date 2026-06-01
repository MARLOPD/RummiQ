package com.rummyq.websocket.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.scene.paint.Color;

public class TileDTO {
    private String numero;
    private String color;
    private boolean esComodin;

    public TileDTO() {
    }

    public TileDTO(String numero, String color, boolean esComodin) {
        this.numero = numero;
        this.color = color;
        this.esComodin = esComodin;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getEsComodin() {
        return esComodin;
    }

    public void setEsComodin(boolean esComodin) {
        this.esComodin = esComodin;
    }

    @JsonIgnore
    public Color getColorJavaFX() {
        return Color.valueOf(this.color.toUpperCase());
    }

    @Override
    public String toString() {
        return esComodin ? "Comodin" : (numero + " " + color);
    }
}
