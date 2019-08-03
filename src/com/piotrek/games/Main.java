package com.piotrek.games;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DurenHelper helper = new DurenHelper();

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
            System.out.println("Kozyr " + talia.getKozyr().getBackgroundColor() + talia.getKozyr().getFiguraColor() + talia.getKozyr().getFigura() + talia.getKozyr().getKolor() + " " + helper.ANSI_RESET);

            helper.drawAllHands(aktualnyGracz, p1, p2);
            helper.drawBoard(broniaceKarty, atakujaceKarty);

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
                            int numerKarty = scanner.nextInt();
                            if (numerKarty <= aktualnyGracz.getReka().size()) {
                                atakujaceKarty.add(aktualnyGracz.getReka().remove(numerKarty-1));
                                aktualnyGracz = broniacyGracz;

                            } else {
                                helper.clearScreen();
                                System.out.println("PODAŁEŚ NIEWŁAŚCIWY NUMER KARTY!!!");
                            }

                        } else {
                            helper.clearScreen();
                            System.out.println("NIE MASZ JUŻ KART W RECE\n\n\n");
                            aktualnyGracz = broniacyGracz;
                        }
                    } else {
                        helper.clearScreen();
                        System.out.println("TERAZ ATAKUJESZ A NIE BRONISZ!");
                    }
                    break;
                case "p": {
                    if (aktualnyGracz == broniacyGracz) {
                        if (aktualnyGracz.getReka().size() > 0) {
                            System.out.println("Podaj numer karty którą chcesz użyć do bicia");
                            int numerKarty = scanner.nextInt();
                            if (numerKarty <= aktualnyGracz.getReka().size()) {
                                if (helper.czyPobije(atakujaceKarty.get(atakujaceKarty.size() - 1), aktualnyGracz.getReka().get(numerKarty - 1), talia)) {
                                    broniaceKarty.add(aktualnyGracz.getReka().remove(numerKarty - 1));
                                    aktualnyGracz = atakujacyGracz;
                                    helper.clearScreen();
                                } else {
                                    helper.clearScreen();
                                    System.out.println("NIE MOŻESZ POBIĆ TĄ KARTĄ!");
                                }
                            } else {
                                helper.clearScreen();
                                System.out.println("PODAŁEŚ NIEWŁĄŚCIWY NUMER KARTY!!!");
                            }

                        } else {
                            helper.clearScreen();
                            System.out.println("NIE MASZ JUŻ KART W RECE\n\n\n");
                            aktualnyGracz = atakujacyGracz;
                        }
                    } else {
                        helper.clearScreen();
                        System.out.println("TERAZ BRONISZ A NIE ATAKUJESZ!");
                    }
                    break;
                }
                case "s": {
                    helper.clearScreen();
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
                        helper.clearScreen();
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
                        helper.clearScreen();
                    }
                    if (atakujacyGracz.getReka().size() < 6) {
                        int brakujeKart = atakujacyGracz.getReka().size();
                        for (int i = 0; i < (6 - brakujeKart); i++) {
                            helper.drawCard(atakujacyGracz, sterta);
                        }
                    }
                    if (broniacyGracz.getReka().size() < 6) {
                        int brakujeKart = broniacyGracz.getReka().size();
                        for (int i = 0; i < (6 - brakujeKart); i++) {
                            helper.drawCard(broniacyGracz, sterta);
                        }
                    }
                    break;
                }

                default: {
                    helper.clearScreen();
                    System.out.println("Podałeś złą akcję!\n\n\n");
                    break;
                }
            }

            if (sterta.size() == 0 && p1.getReka().size() == 0) {
                helper.clearScreen();
                System.out.println("GRACZ " + p2.getName() + " PRZEGRAŁ!!!");
            } else if (sterta.size() == 0 && p2.getReka().size() == 0) {
                helper.clearScreen();
                System.out.println("GRACZ " + p1.getName() + " PRZEGRAŁ!!!");
            }
        }

    }
}
