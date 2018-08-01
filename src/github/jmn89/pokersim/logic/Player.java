package github.jmn89.pokersim.logic;
//@author jmn89

public class Player {

    private String name;
    private Card[] cards;
    private int seatNum;
    private Hand hand;
    private Boolean dead;
//    private Boolean satOut = false;
//    private Boolean inHand;
//    private static int playersLeft = 0;

    public Player(String name, int seatNum) {
        this.name = name;
        this.seatNum = seatNum;
        cards = new Card[2];
        this.dead = false;
    }

    public void setCards(int i, Card c) {
        cards[i] = c;
    }

    public void setHand(Hand h) { hand = h; }

    public int getSeatNum() {
        return seatNum;
    }

    public Boolean isDead() {
        return dead;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() { return hand; }

    public Card[] getCards() { return cards; }
}
