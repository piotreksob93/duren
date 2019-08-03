package com.piotrek.games;

import java.util.List;
import java.util.Queue;

class DurenHelper {

    final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_YELLOW = "\u001b[33m";

    void drawCard(Player p, Queue<Card> q) {
        if (!(q.size() == 0)) {
            Card card = q.remove();
            List<Card> rekaGracza = p.getReka();
            rekaGracza.add(card);
            p.setReka(rekaGracza);
        }
    }

    boolean czyPobije(Card card, Card card1, Deck talia) {
        //sprawdzam czy karta bijaca jest kozyrem
        if (card1.getKolor().equals(talia.getKozyr().getKolor())) {
            //sprawdzam czy karta bita jest kozyrem
            if (card.getKolor().equals(talia.getKozyr().getKolor())) {
                //sprawdzam wiekszy kozyr
                return (card1.compareTo(card)) > 0;

            } else {
                //kozyr bije zwykłą kartę
                return true;
            }
        } else {
            if (card.getKolor().equals(talia.getKozyr().getKolor())) {
                //nie możesz bić kozyra zwkłą kartą
                return false;
            } else {
                //porównuje kolory zwykłych kart
                if (card1.getKolor().equals(card.getKolor())) {
                    //porównuje ktora karta jest mocniejsza
                    return (card1.compareTo(card)) > 0;

                } else {
                    //karty zwykłę można bić tylko takim samym kolorem
                    return false;
                }
            }
        }
    }

    void drawBoard(List<Card> broniace, List<Card> atakuja) {
        System.out.println("KARTY DO ODPARCIA:");
        for (Card karta : atakuja) {
            if (atakuja.size() == 0)
                System.out.println();
            System.out.print((atakuja.indexOf(karta) + 1) + karta.getBackgroundColor() + karta.getFiguraColor() + karta.getFigura() + karta.getKolor() + ANSI_RESET + " ");
        }
        System.out.println();

        System.out.println("KARTY ODPIERAJACE:");
        if (broniace.size() == 0)
            System.out.println();
        for (Card karta : broniace) {
            System.out.print((broniace.indexOf(karta) + 1) + karta.getBackgroundColor() + karta.getFiguraColor() + karta.getFigura() + karta.getKolor() + ANSI_RESET + " ");
        }
        System.out.println();
    }

    void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    void drawAllHands(Player a, Player... p) {
        for (Player pe : p)
            printPlayerHand(pe, a);
    }

    private void printDeck(Deck talia) {
        int i = 1;
        for (Card karta : talia.cards) {
            System.out.print(karta.getBackgroundColor() + karta.getFiguraColor() + karta.getFigura() + karta.getKolor() + " " + ANSI_RESET);
            if (i == 13) {
                i = 1;
                System.out.println();
            } else
                i++;
        }
        System.out.println();
    }

    private void printPlayerHand(Player p, Player current) {
        System.out.println("REKA GRACZA " + p.getName());

        for (Card karta : p.getReka()) {
            if (current.getName().equals(p.getName())) {
                System.out.print((p.getReka().indexOf(karta) + 1) + karta.getBackgroundColor() + karta.getFiguraColor() + karta.getFigura() + karta.getKolor() + ANSI_RESET + " ");
            } else {
                System.out.print((p.getReka().indexOf(karta) + 1) + karta.getBackgroundColor() + ANSI_YELLOW + "☻☺" + ANSI_RESET + " ");
            }
        }
        System.out.println();
    }

    private void printCardsLeftOnTheDeck(Queue<Card> sterta) {
        int i = 1;
        for (Card karta : sterta) {
            System.out.print(karta.getBackgroundColor() + karta.getFiguraColor() + karta.getFigura() + karta.getKolor() + ANSI_RESET + " ");
            if (i == 13) {
                i = 1;
                System.out.println("\n");
            } else
                i++;
        }
        System.out.println();
    }

    boolean czyMoznaDolozyc(Player aktualnyGracz, List<Card> atakujaceKarty, List<Card> broniaceKarty, int numerKarty) {
        if (atakujaceKarty.size() != 0) {
            for (Card karta : atakujaceKarty) {
                if (aktualnyGracz.getReka().get(numerKarty - 1).getKolor().equals(karta.getKolor()) || aktualnyGracz.getReka().get(numerKarty - 1).getFigura().equals(karta.getFigura())) {
                    return true;
                }
                for (Card karta1 : broniaceKarty) {
                    if (aktualnyGracz.getReka().get(numerKarty - 1).getKolor().equals(karta1.getKolor()) || aktualnyGracz.getReka().get(numerKarty - 1).getFigura().equals(karta1.getFigura())) {
                        return true;
                    }
                }
            }
        } else {
            return true;
        }
        return false;
    }


}
