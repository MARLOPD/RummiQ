package com.rummyq.websocket.dto;

public class TileDTO {
    private Integer number;
    private String color;
    private boolean isJoker;

    public TileDTO() {
    }

    public TileDTO(Integer number, String color, boolean isJoker) {
        this.number = number;
        this.color = color;
        this.isJoker = isJoker;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getIsJoker() {
        return isJoker;
    }

    public void setIsJoker(boolean isJoker) {
        this.isJoker = isJoker;
    }

    @Override
    public String toString() {
        return isJoker ? "Comodin" : (number + " " + color);
    }
}
