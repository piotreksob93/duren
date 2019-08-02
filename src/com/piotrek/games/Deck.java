package com.piotrek.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

    public List<Card> cards = new ArrayList<>();

    private Card kozyr;

    public Deck() {
        initDeck();
        shuffleDeck();
    }

    public Card getKozyr() {
        return kozyr;
    }

    public void setKozyr(Card kozyr) {
        this.kozyr = kozyr;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    private void initDeck() {
        String[] kolor1 = {"♥", "♦", "♣", "♠"};
        String[] figura1 = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "W", "Q", "K", "A"};
        int[] wagi={2,3,4,5,6,7,8,9,10,11,12,13,14};

        for (String kolor : kolor1) {
            int i =0;
            for (String figura : figura1) {
                if (kolor.equals("♣") || kolor.equals("♠")) {
                    cards.add(new Card(kolor, figura, "\u001B[34m",wagi[i]));
                } else {
                    cards.add(new Card(kolor, figura, "\u001B[31m",wagi[i]));
                }
                i+=1;
            }
        }
//        cards.add(new Card("J", " ", "\u001B[31m"));
//        cards.add(new Card("J", " ", "\u001B[34m"));
    }

    private void shuffleDeck() {
        int i = 0;
        Random rand = new Random();
        while (i < 1000) {
            int j = rand.nextInt(52);
            int k = rand.nextInt(52);
            Collections.swap(cards, j, k);
            i++;
        }

        kozyr=cards.get(cards.size()-1);
    }

    public void dealTheCards(Player... p) {
        int i=0;
        for (Player player:p){
            List<Card> lista = new ArrayList<>();
            for(int j=0;j<6;j++){
                lista.add(cards.get(i+j));
            }
            player.setReka(lista);
            i+=6;
        }
    }

}
