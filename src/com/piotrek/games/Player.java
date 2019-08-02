package com.piotrek.games;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Card> reka = new ArrayList<>();
    private String name;
    private static int numberOfPlayers=0;

    public Player(String name) {
        this.name = name;
        numberOfPlayers+=1;
    }

    public List<Card> getReka() {
        return reka;
    }

    public void setReka(List<Card> reka) {
        this.reka = reka;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
