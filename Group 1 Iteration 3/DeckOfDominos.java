import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class DeckOfDominos
{

    private ArrayList<Domino> deck;
    private ArrayList<Domino> randomDominos;
    private int dominosRemaining;
    private final int NUM_OF_DOMINOS = 48;
    private final int CARDS_PER_RND = 4;
    private Random rand;

    public DeckOfDominos()
    {
        dominosRemaining = NUM_OF_DOMINOS; 
        rand = new Random();
    }

    public void createDeck()
    {
        deck = new ArrayList<>();

        Domino domino1 = new Domino(Color.YELLOW, Color.YELLOW, 0, 0, 1);
        deck.add(domino1);

        Domino domino2 = new Domino(Color.YELLOW, Color.YELLOW, 0, 0, 2);
        deck.add(domino2);

        Domino domino3 = new Domino(Color.GREEN, Color.GREEN, 0, 0, 3);
        deck.add(domino3);

        Domino domino4 = new Domino(Color.GREEN, Color.GREEN, 0, 0, 4);
        deck.add(domino4);

        Domino domino5 = new Domino(Color.GREEN, Color.GREEN, 0, 0, 5);
        deck.add(domino5);

        Domino domino6 = new Domino(Color.GREEN, Color.GREEN, 0, 0, 6);
        deck.add(domino6);

        Domino domino7 = new Domino(Color.BLUE, Color.BLUE, 0, 0, 7);
        deck.add(domino7);

        Domino domino8 = new Domino(Color.BLUE, Color.BLUE, 0, 0, 8);
        deck.add(domino8);

        Domino domino9 = new Domino(Color.BLUE, Color.BLUE, 0, 0, 9);
        deck.add(domino9);
        
        Domino domino10 = new Domino(Color.CYAN, Color.CYAN, 0, 0, 10);
        deck.add(domino10);
        
        Domino domino11 = new Domino(Color.CYAN, Color.CYAN, 0, 0, 11);
        deck.add(domino11);

        Domino domino12 = new Domino(Color.LIGHT_GRAY, Color.LIGHT_GRAY, 0, 0, 12);
        deck.add(domino12);

        Domino domino13 = new Domino(Color.YELLOW, Color.GREEN, 0, 0, 13);
        deck.add(domino13);

        Domino domino14 = new Domino(Color.YELLOW, Color.BLUE, 0, 0, 14);
        deck.add(domino14);

        Domino domino15 = new Domino(Color.YELLOW, Color.CYAN, 0, 0, 15);
        deck.add(domino15);

        Domino domino16 = new Domino(Color.YELLOW, Color.LIGHT_GRAY, 0, 0, 16);
        deck.add(domino16);

        Domino domino17 = new Domino(Color.GREEN, Color.BLUE, 0, 0, 17);
        deck.add(domino17);

        Domino domino18 = new Domino(Color.GREEN, Color.CYAN, 0, 0, 18);
        deck.add(domino18);

        Domino domino19 = new Domino(Color.YELLOW, Color.GREEN, 1, 0, 19);
        deck.add(domino19);

        Domino domino20 = new Domino(Color.YELLOW, Color.BLUE, 1, 0, 20);
        deck.add(domino20);

        Domino domino21 = new Domino(Color.YELLOW, Color.CYAN, 1, 0, 21);
        deck.add(domino21);

        Domino domino22 = new Domino(Color.YELLOW, Color.LIGHT_GRAY, 1, 0, 22);
        deck.add(domino22);

        Domino domino23 = new Domino(Color.YELLOW, Color.BLACK, 1, 0, 23);
        deck.add(domino23);

        Domino domino24 = new Domino(Color.GREEN, Color.YELLOW, 1, 0, 24);
        deck.add(domino24);

        Domino domino25 = new Domino(Color.GREEN, Color.YELLOW, 1, 0, 25);
        deck.add(domino25);

        Domino domino26 = new Domino(Color.GREEN, Color.YELLOW, 1, 0, 26);
        deck.add(domino26);

        Domino domino27 = new Domino(Color.GREEN, Color.YELLOW, 1, 0, 27);
        deck.add(domino27);

        Domino domino28 = new Domino(Color.GREEN, Color.BLUE, 1, 0, 28);
        deck.add(domino28);

        Domino domino29 = new Domino(Color.GREEN, Color.CYAN, 1, 0, 29);
        deck.add(domino29);

        Domino domino30 = new Domino(Color.BLUE, Color.YELLOW, 1, 0, 30);
        deck.add(domino30);

        Domino domino31 = new Domino(Color.BLUE, Color.YELLOW, 1, 0, 31);
        deck.add(domino31);

        Domino domino32 = new Domino(Color.BLUE, Color.GREEN, 1, 0, 32);
        deck.add(domino32);

        Domino domino33 = new Domino(Color.BLUE, Color.GREEN, 1, 0, 33);
        deck.add(domino33);

        Domino domino34 = new Domino(Color.BLUE, Color.GREEN, 1, 0, 34);
        deck.add(domino34);

        Domino domino35 = new Domino(Color.BLUE, Color.GREEN, 1, 0, 35);
        deck.add(domino35);

        Domino domino36 = new Domino(Color.YELLOW, Color.CYAN, 0, 1, 36);
        deck.add(domino36);

        Domino domino37 = new Domino(Color.BLUE, Color.CYAN, 0, 1, 37);
        deck.add(domino37);

        Domino domino38 = new Domino(Color.YELLOW, Color.LIGHT_GRAY, 0, 1, 38);
        deck.add(domino38);

        Domino domino39 = new Domino(Color.CYAN, Color.LIGHT_GRAY, 0, 1, 39);
        deck.add(domino39);

        Domino domino40 = new Domino(Color.BLACK, Color.YELLOW, 1, 0, 40);
        deck.add(domino40);

        Domino domino41 = new Domino(Color.YELLOW, Color.CYAN, 0, 2, 41);
        deck.add(domino41);

        Domino domino42 = new Domino(Color.BLUE, Color.CYAN, 0, 2, 42);
        deck.add(domino42);

        Domino domino43 = new Domino(Color.YELLOW, Color.LIGHT_GRAY, 0, 2, 43);
        deck.add(domino43);

        Domino domino44 = new Domino(Color.CYAN, Color.LIGHT_GRAY, 0, 2, 44);
        deck.add(domino44);

        Domino domino45 = new Domino(Color.BLACK, Color.YELLOW, 2, 0, 45);
        deck.add(domino45);

        Domino domino46 = new Domino(Color.LIGHT_GRAY, Color.BLACK, 0, 2, 46);
        deck.add(domino46);

        Domino domino47 = new Domino(Color.LIGHT_GRAY, Color.BLACK, 0, 2, 47);
        deck.add(domino47);

        Domino domino48 = new Domino(Color.YELLOW, Color.BLACK, 0, 3, 48);
        deck.add(domino48);

    }

    public ArrayList<Domino> randomDominos()
    {
        int i;
        int randomNum;
        randomDominos = new ArrayList<>();

        for (i = 0; i < CARDS_PER_RND; i++){
            randomNum = rand.nextInt(dominosRemaining);
            randomDominos.add(deck.get(randomNum));
            deck.remove(randomNum);
            --dominosRemaining;

        }

        Collections.sort(randomDominos, Comparator.comparing(Domino::getNumericalVal));

        return randomDominos;
    }

    public int getRemainingDominos()
    {
        return dominosRemaining;
    }



}