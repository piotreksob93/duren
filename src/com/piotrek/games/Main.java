package com.piotrek.games;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {

        Deck talia = new Deck();

        Player p1 = new Player("Piotrek");

        Player p2 = new Player("Mateusz");

//        Player p3 = new Player("Mateusz");

        Player atakujacyGracz = p1;
        Player broniacyGracz = p2;
        Player aktualnyGracz = p1;


        List<Card> lista = talia.getCards();

        List<Card> broniaceKarty = new ArrayList<>();
        List<Card> atakujaceKarty = new ArrayList<>();

        Queue<Card> sterta = new LinkedList<>(lista.subList(Player.getNumberOfPlayers() * 6, talia.getCards().size()));

        talia.dealTheCards(p1, p2);

        while (p1.getReka().size() > 0 && p2.getReka().size() > 0 || sterta.size() > 0) {
            System.out.println("W talii zostało " + sterta.size() + " kart");
            System.out.println("Kozyr " + talia.getKozyr().getBackgroundColor() + talia.getKozyr().getFiguraColor() + talia.getKozyr().getFigura() + talia.getKozyr().getKolor() + " " + ANSI_RESET);

            drawAllHands(p1, p2);
            drawBoard(broniaceKarty, atakujaceKarty);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Aktualny gracz: " + aktualnyGracz.getName());
            if (aktualnyGracz == broniacyGracz) System.out.println("BRONISZ SIE");
            else System.out.println("ATAKUJESZ");
            System.out.println("Wprowadz akcję");
            if (aktualnyGracz == atakujacyGracz)
                System.out.println("Akcje: d - dołóż karte, s - przerwij gre, k - koniec ruchu");
            else System.out.println("Akcje: s - przerwij gre, p - pobij karte");
            String akcja = scanner.nextLine();
            switch (akcja) {
                case "d":
                    if (aktualnyGracz == atakujacyGracz) {
                        if (aktualnyGracz.getReka().size() > 0) {
                            System.out.println("Podaj numer karty do dołożenia");
                            atakujaceKarty.add(aktualnyGracz.getReka().remove(scanner.nextInt() - 1));
                            aktualnyGracz = broniacyGracz;
                            clearScreen();
                        } else {
                            clearScreen();
                            System.out.println("NIE MASZ JUŻ KART W RECE\n\n\n");
                            aktualnyGracz = broniacyGracz;
                        }
                    } else {
                        clearScreen();
                        System.out.println("TERAZ ATAKUJESZ A NIE BRONISZ!");
                    }
                    break;
                case "p": {
                    if (aktualnyGracz == broniacyGracz) {
                        if (aktualnyGracz.getReka().size() > 0) {
                            System.out.println("Podaj numer karty którą chcesz użyć do bicia");
                            int numer = scanner.nextInt();
                            if (czyPobije(atakujaceKarty.get(atakujaceKarty.size() - 1), aktualnyGracz.getReka().get(numer - 1), talia)) {
                                broniaceKarty.add(aktualnyGracz.getReka().remove(numer - 1));
                                aktualnyGracz = atakujacyGracz;
                                clearScreen();
                            } else {
                                clearScreen();
                                System.out.println("NIE MOŻESZ POBIĆ TĄ KARTĄ!");
                            }
                        } else {
                            clearScreen();
                            System.out.println("NIE MASZ JUŻ KART W RECE\n\n\n");
                            aktualnyGracz = atakujacyGracz;
                        }
                    } else {
                        clearScreen();
                        System.out.println("TERAZ BRONISZ A NIE ATAKUJESZ!");
                    }
                    break;
                }
                case "s": {
                    clearScreen();
                    System.out.println("GRA PRZERWANA!");
                    return;
                }
                case "k": {
                    if (aktualnyGracz == atakujacyGracz) {
                        Player temp = broniacyGracz;
                        broniacyGracz = atakujacyGracz;
                        atakujacyGracz = temp;
                        aktualnyGracz = atakujacyGracz;
                        broniaceKarty.clear();
                        atakujaceKarty.clear();
                        clearScreen();
                    } else {
                        if (broniaceKarty.size() < atakujaceKarty.size()) {
                            List<Card> doZabrania = new ArrayList<>();
                            doZabrania.addAll(broniacyGracz.getReka());
                            doZabrania.addAll(broniaceKarty);
                            doZabrania.addAll(atakujaceKarty);
                            broniacyGracz.setReka(doZabrania);
                        }
                        Player temp = broniacyGracz;
                        broniacyGracz = atakujacyGracz;
                        atakujacyGracz = temp;
                        aktualnyGracz = atakujacyGracz;
                        broniaceKarty.clear();
                        atakujaceKarty.clear();
                        clearScreen();
                    }
                    if (atakujacyGracz.getReka().size() < 6) {
                        int brakujeKart=atakujacyGracz.getReka().size();
                        for (int i = 0; i < (6 - brakujeKart); i++) {
                            drawCard(atakujacyGracz, sterta);
                        }
                    }
                    if (broniacyGracz.getReka().size() < 6) {
                        int brakujeKart=broniacyGracz.getReka().size();
                        for (int i = 0; i < (6 - brakujeKart); i++) {
                            drawCard(broniacyGracz, sterta);
                        }
                    }
                    break;
                }

                default: {
                    clearScreen();
                    System.out.println("Podałeś złą akcję!\n\n\n");
                    break;
                }
            }
            if (sterta.size() == 0 && p1.getReka().size() == 0) {
                clearScreen();
                System.out.println("GRACZ " + p2.getName() + " PRZEGRAŁ!!!");
            } else if (sterta.size() == 0 && p2.getReka().size() == 0) {
                clearScreen();
                System.out.println("GRACZ " + p1.getName() + " PRZEGRAŁ!!!");
            }
        }

    }

    private static boolean czyPobije(Card card, Card card1, Deck talia) {
        //sprawdzam czy karta bijaca jest kozyrem
        if (card1.getKolor() == talia.getKozyr().getKolor()) {
            //sprawdzam czy karta bita jest kozyrem
            if (card.getKolor() == talia.getKozyr().getKolor()) {
                //sprawdzam wiekszy kozyr
                if ((card1.compareTo(card)) == 1)
                    return true;
                else
                    return false;

            } else {
                //kozyr bije zwykłą kartę
                return true;
            }
        } else {
            if (card.getKolor() == talia.getKozyr().getKolor()) {
                //nie możesz bić kozyra zwkłą kartą
                return false;
            } else {
                //porównuje kolory zwykłych kart
                if (card1.getKolor() == card.getKolor()) {
                    //porównuje ktora karta jest mocniejsza
                    if ((card1.compareTo(card)) == 1)
                        return true;
                    else
                        return false;

                } else {
                    //karty zwykłę można bić tylko takim samym kolorem
                    return false;
                }
            }
        }
    }

    private static void drawBoard(List<Card> broniace, List<Card> atakuja) {
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

    private static void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    private static void drawAllHands(Player... p) {
        for (Player pe : p)
            printPlayerHand(pe);
    }

    private static void printDeck(Deck talia) {
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

    private static void printPlayerHand(Player p) {
        System.out.println("REKA GRACZA " + p.getName());

        for (Card karta : p.getReka()) {
            System.out.print((p.getReka().indexOf(karta) + 1) + karta.getBackgroundColor() + karta.getFiguraColor() + karta.getFigura() + karta.getKolor() + ANSI_RESET + " ");
        }
        System.out.println();
    }

    private static void printCardsLeftOnTheDeck(Queue<Card> sterta) {
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

    private static void drawCard(Player p, Queue<Card> q) {
        if (!(q.size() == 0)) {
            Card card = q.remove();
            List<Card> rekaGracza = p.getReka();
            rekaGracza.add(card);
            p.setReka(rekaGracza);
        }
    }
}
