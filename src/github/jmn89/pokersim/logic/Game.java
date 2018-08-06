package github.jmn89.pokersim.logic;
import java.util.*;
//@author jmn89

public class Game {
    private final int initialPlayerCount;
    private Player[] players;
    private int buttonPosition;
    private Card[] board;
    private ArrayList<Integer> openSeats;
    private ArrayList<Integer> playerTraversalOrder;

    Game(List<String> names) {

        initialPlayerCount = names.size();

        //randomise button position
        Random rn = new Random();
        buttonPosition = rn.nextInt(initialPlayerCount) + 1;
        //move button back 1; as the button will move forward 1 at start of dealNewHand()
        if (buttonPosition == 1) {
            buttonPosition = initialPlayerCount;
        } else {
            buttonPosition--;
        }

        //create open seats
        openSeats = new ArrayList<>();
        for (int i = 1; i <= initialPlayerCount; i++) {
            openSeats.add(i);
        }
        Collections.shuffle(openSeats);

        //create Players
        players = new Player[initialPlayerCount];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(names.get(i), openSeats.get(0));
            openSeats.remove(0);
        }

        //create Traversal Order
        playerTraversalOrder = createPlayerTraversalOrder(buttonPosition);
    }

    public void dealNewHand() {
        Deck deck = new Deck();
        moveButton();

        //deal cards to players
        int roundsDealt = 0;
        while (roundsDealt < 2) {
            for (Integer x : playerTraversalOrder) {
                int playerIndex = x;
                if (!players[playerIndex].isDead()) {
                    players[playerIndex].setCards(roundsDealt, deck.dealCard());
                }
            }
            roundsDealt++;
        }

        //flop
        board = new Card[5];
        deck.burnCard();
        board[0] = deck.dealCard();
        board[1] = deck.dealCard();
        board[2] = deck.dealCard();
        //turn
        deck.burnCard();
        board[3] = deck.dealCard();
        //river
        deck.burnCard();
        board[4] = deck.dealCard();
    }

    public void concludeHand() {
        Hand[] allHands = new Hand[initialPlayerCount];

        for (int i = 0; i < players.length; i++) {
            //find Players hand
            Card[] allCards = new Card[7];
            System.arraycopy(players[i].getCards(), 0, allCards, 0, players[i].getCards().length);
            System.arraycopy(board, 0, allCards, 2, board.length);
            players[i].setHand(HandFinder.findBestHand(allCards));
            allHands[i] = players[i].getHand();

            //Print player name and hand
            System.out.println(
                    players[i].getName() + "\t==\t" +
                            players[i].getCards()[0].toString()
                            + " \t" + players[i].getCards()[1].toString()
                            + " \t== " + players[i].getHand().toString()
            );
        }

        //find Winning Hand
        Hand winningHand = HandFinder.findWinningHand(allHands);

        //print board
        StringBuilder boardAsString = new StringBuilder("BRD \t==\t");
        for (Card c : board) {
            boardAsString.append(" ").append(c.toString());
        }
        System.out.println("::::::::::::::::::\n" + boardAsString);

        //print Winners
        Arrays.stream(players)
                .filter(pl -> Arrays.equals(pl.getHand().getValue(), winningHand.getValue()))
                .forEach(pl -> System.out.println("Winner \t==\t " + pl.getName()));
    }

    private ArrayList<Integer> createPlayerTraversalOrder(int button) {
        ArrayList<Integer> order = new ArrayList<>();
        for (int i = button+1; i <= players.length; i++) {
            order.add( getPlayerIndexFromSeatNum(i) );
        }
        for (int i = 1; i <= button; i++) {
            order.add( getPlayerIndexFromSeatNum(i) );
        }
        return order;
    }

    private int getPlayerIndexFromSeatNum(int target) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getSeatNum() == target) {
                return i;
            }
        }
        return -1;
    }

    private void moveButton() {
        if (buttonPosition == players.length) {
            buttonPosition = 1;
        } else {
            buttonPosition++;
        }
        int head = playerTraversalOrder.remove(0);
        playerTraversalOrder.add(head);
    }
}
