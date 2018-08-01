package github.jmn89.pokersim.logic;
//@author jmn89

import java.util.ArrayList;
import org.apache.commons.math3.util.Combinations;

public class HandFinder {
    
    private static int count = 0;
    private static Hand bestHand;
    private static Card[] allCards;
    private static Hand[] allHands;
    private static final ArrayList<int[]> HAND_COMBINATIONS;

    static 
    {
        ArrayList<int[]> combinations = new ArrayList<>();
        for (int[] set : new Combinations(7, 5)) {
            combinations.add(set);
        }
        HAND_COMBINATIONS = combinations;        
    }
    
    public static Hand findBestHand(Card[] cards) {
        if (count == 0) {
            allCards = cards;
            bestHand = createHandFromComb(HAND_COMBINATIONS.get(count));
        }
        if (count == HAND_COMBINATIONS.size() - 1) {
            Hand h = createHandFromComb(HAND_COMBINATIONS.get(count));
            count = 0;
            return getBestOrEqualHandFrom(bestHand, h);
        }
        Hand nextHand = createHandFromComb(HAND_COMBINATIONS.get(count + 1));
        bestHand = getBestOrEqualHandFrom(bestHand, nextHand);
        count++;
        return findBestHand(allCards);
    }

    public static Hand findWinningHand(Hand[] hands) {
        if (count == 0) {
            allHands = hands;
            bestHand = allHands[count];
        }
        if (count == allHands.length - 1) {
            count = 0;
            return getBestOrEqualHandFrom(bestHand, allHands[count]);
        }
        Hand nextHand = allHands[count+1];
        bestHand = getBestOrEqualHandFrom(bestHand, nextHand);
        count++;
        return findWinningHand(allHands);
    }
    
    private static Hand getBestOrEqualHandFrom(Hand h1, Hand h2) {
        for (int x = 0; x < h1.getValue().length; x++)
        {
            if (h1.getValue()[x] > h2.getValue()[x])
                return h1;
            else if (h1.getValue()[x] < h2.getValue()[x])
                return h2;
        }
        return h1;
    }
    
   private static Hand createHandFromComb(int[] combination) {
	Card[] cards = new Card[5];
	for (int i=0; i < cards.length; i++) {
            cards[i] = allCards[combination[i]];
	}
	return new Hand(cards);
   }
}
