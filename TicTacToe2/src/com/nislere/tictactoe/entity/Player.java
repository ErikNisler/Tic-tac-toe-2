package com.nislere.tictactoe.entity;

import com.nislere.tictactoe.marker.MarkerTypeEnum;

import java.util.Objects;

public class Player {

    private String name;
    private boolean playerTurn;
    private final MarkerTypeEnum markerType;

    public Player(String name, final MarkerTypeEnum markerType, boolean playerTurn) {
        this.name = name;
        this.markerType = markerType;
        this.playerTurn = playerTurn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MarkerTypeEnum getMarkerType() {
        return markerType;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) && markerType == player.markerType && playerTurn == player.playerTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, markerType, playerTurn);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", playerTurn=" + playerTurn +
                ", markerType=" + markerType +
                '}';
    }
}
