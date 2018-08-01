package github.jmn89.pokersim.logic;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
//@author jmn89

public class Deck {

    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (short a = 0; a < 4; a++) {
            for (short b = 0; b < 13; b++) {
                cards.add(new Card(a, b));
            }
        }
        shuffleDeck();
    }

    private void shuffleDeck() {
        for (int i = cards.size() - 1; i > 0; i--) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, cards.size() - 1);
            Card temp = this.cards.get(i);
            cards.set(i, this.cards.get(randomNum));
            cards.set(randomNum, temp);
        }
    }

    public Card dealCard() {
        return cards.remove(cards.size() - 1);
    }

    public void burnCard() {
        cards.remove(cards.size() - 1);
    }
}
