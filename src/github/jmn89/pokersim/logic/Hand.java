package github.jmn89.pokersim.logic;
import java.util.ArrayList;
import org.apache.commons.math3.util.Combinations;

public class Hand
{
    private int[] value;
    public static final ArrayList<int[]> HAND_COMBINATIONS;
    
    static 
    {
        ArrayList<int[]> combinations = new ArrayList<>();
        for (int[] set : new Combinations(7, 5)) {
            combinations.add(set);
        }
        HAND_COMBINATIONS = combinations;        
    }

    public Hand(Card[] cards)
    {
        value = new int[6];

        //rankFreq stores the frequency of each card rank in a hand
        int[] rankFrequencies = new int[13];
        for (Card card : cards) {
            rankFrequencies[card.getRank()]++;
        }

        //pairs/2pairs/trips/quads check
        int higherPairRank = 0;
        int cardsInHigherPair = 1;
        int lowerPairRank = 0;
        int cardsInLowerPair = 1;
        for (int x = rankFrequencies.length-1; x >= 0; x--)
        {
            if (rankFrequencies[x] > cardsInHigherPair)
            {
                if (cardsInHigherPair != 1)
                {
                    cardsInLowerPair = cardsInHigherPair;
                    lowerPairRank = higherPairRank;
                }
                cardsInHigherPair = rankFrequencies[x];
                higherPairRank = x;
            }
            else if (rankFrequencies[x] > cardsInLowerPair)
            {
                cardsInLowerPair = rankFrequencies[x];
                lowerPairRank = x;
            }
        }

        //straight check
        boolean straight = false;
        int straightHighCard = 0;
        if (rankFrequencies[0] == 1 && rankFrequencies[1] == 1 &&
                rankFrequencies[2] == 1 && rankFrequencies[3] == 1 &&
                    rankFrequencies[12] == 1)
        {
            straight = true;
            straightHighCard = 5;
        }
        if (!straight)
        {
            for (int x = 0; x < rankFrequencies.length - 4; x++) {
                if (rankFrequencies[x] == 1 && rankFrequencies[x + 1] == 1 &&
                        rankFrequencies[x + 2] == 1 && rankFrequencies[x + 3] == 1 &&
                        rankFrequencies[x + 4] == 1) {
                    straight = true;
                    straightHighCard = x + 4;
                    break;
                }
            }
        }

        //flush check
        boolean flush = true;
        for (int x = 0; x < cards.length-1; x++) {
            if (!cards[x].getSuit().equals(cards[x + 1].getSuit())) {
                flush = false;
            }
        }
        
        //orderedRanks array stores each card rank of a Hand in desc. order
        int[] handInRankOrder = new int[5];
        int index = 0;
        for (int x = rankFrequencies.length - 1; x >= 0; x--)
        {
            if (rankFrequencies[x] == 1)    //ignore pairs/trips/quads
            {
                handInRankOrder[index] = x;
                index++;
            }
        }

        //start hand evaluation
        //high card
        if (cardsInHigherPair == 1) {
            value[0] = 1;
            value[1] = handInRankOrder[0];
            value[2] = handInRankOrder[1];
            value[3] = handInRankOrder[2];
            value[4] = handInRankOrder[3];
            value[5] = handInRankOrder[4];
        }
        //pair
        if (cardsInHigherPair == 2 && cardsInLowerPair == 1)
        {
            value[0] = 2;
            value[1] = higherPairRank;
            value[2] = handInRankOrder[0];
            value[3] = handInRankOrder[1];
            value[4] = handInRankOrder[2];
        }
        //2 pair
        if (cardsInHigherPair == 2 && cardsInLowerPair == 2)
        {
            value[0] = 3;
            value[1] = higherPairRank;
            value[2] = lowerPairRank;
            value[3] = handInRankOrder[0];
        }
        //trips
        if (cardsInHigherPair == 3 && cardsInLowerPair != 2)
        {
            value[0] = 4;
            value[1] = higherPairRank;
            value[2] = handInRankOrder[0];
            value[3] = handInRankOrder[1];
        }
        //straight
        if (straight && !flush)
        {
            value[0] = 5;
            value[1] = straightHighCard;
        }
        //flush
        if (flush && !straight)
        {
            value[0] = 6;
            value[1] = handInRankOrder[0];
            value[2] = handInRankOrder[1];
            value[3] = handInRankOrder[2];
            value[4] = handInRankOrder[3];
            value[5] = handInRankOrder[4];
        }
        //full house
        if (cardsInHigherPair == 3 && cardsInLowerPair == 2)
        {
            value[0] = 7;
            value[1] = higherPairRank;
            value[2] = lowerPairRank;
        }
        //quads
        if (cardsInHigherPair == 4)
        {
            value[0] = 8;
            value[1] = higherPairRank;
            value[2] = handInRankOrder[0];
        }
        //straight flush
        if (straight && flush)
        {
            value[0] = 9;
            value[1] = straightHighCard;
        }
    }

    @Override
    public String toString() {
        String s;
        switch(value[0])
        {
            case 1:
                s = "High Card";
                break;
            case 2:
                s = "Pair of " + Card.rankToString(value[1]) + "\'s";
                break;
            case 3:
                s = "Two Pair " + Card.rankToString(value[1]) + "\'s and " +
                                Card.rankToString(value[2]) + "\'s";
                break;
            case 4:
                s = "Three of a Kind " + Card.rankToString(value[1]) + "\'s";
                break;
            case 5:
                s = Card.rankToString(value[1]) + " High Straight";
                break;
            case 6:
                s = Card.rankToString(value[1]) + " High Flush";
                break;
            case 7:
                s = "Full House " + Card.rankToString(value[1]) + "\'s over " +
                                  Card.rankToString(value[2]) + "\'s";
                break;
            case 8:
                s = "Four of a Kind " + Card.rankToString(value[1]) + "\'s";
                break;
            case 9:
                s = Card.rankToString(value[1]) + " High Straight Flush";
                break;
            default:
                s = "Error! Hand.value[0] == " + String.valueOf(value[0]);
        }
        return s;
    }

    public int compareTo(Hand that) {
        for (int x = 0; x < value.length; x++)
        {
            if (this.value[x] > that.value[x])
                return 1;
            else if (this.value[x] < that.value[x])
                return -1;
        }
        return 0;
    }

    public int[] getValue() {
        return this.value;
    }
}
