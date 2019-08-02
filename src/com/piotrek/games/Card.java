package com.piotrek.games;

public class Card implements Comparable<Card> {

    private String kolor;
    private String figura;
    private String backgroundColor;
    private String figuraColor;
    private int waga;

    public Card(String kolor, String figura, String figuraColor, int waga) {
        this.kolor = kolor;
        this.figura = figura;
        this.backgroundColor = "\u001B[40m";
        this.figuraColor = figuraColor;
        this.waga = waga;

    }

    public String getKolor() {
        return kolor;
    }

    public void setKolor(String kolor) {
        this.kolor = kolor;
    }

    public String getFigura() {
        return figura;
    }

    public void setFigura(String figura) {
        this.figura = figura;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getFiguraColor() {
        return figuraColor;
    }

    public void setFiguraColor(String figuraColor) {
        this.figuraColor = figuraColor;
    }

    public int getWaga() {
        return waga;
    }

    public void setWaga(int waga) {
        this.waga = waga;
    }

    @Override
    public String toString() {
        return "Card{" +
                "kolor='" + kolor + '\'' +
                ", figura='" + figura + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", figuraColor='" + figuraColor + '\'' +
                '}' + "\u001B[0m";
    }

    @Override
    public int compareTo(Card o) {
        if (this.waga > o.waga) return 1;
        if (this.waga < o.waga) return -1;
        else
            return 0;
    }
}
