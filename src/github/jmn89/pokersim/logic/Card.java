package github.jmn89.pokersim.logic;

public class Card
{
    private final short rank;
    private final short suit;
    private static final String[] SUITS = {"Hearts", "Spades", "Diamonds", "Clubs"};
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9",
                                        "10", "Jack", "Queen", "King", "Ace"};

    public Card(short suit, short rank) {
        this.rank=rank;
        this.suit=suit;
    }

    @Override
    public String toString() {
        String rankStr;
        if (rank == 8) {
            rankStr = rankToString(rank);
        } else {
            rankStr = String.valueOf(RANKS[rank].charAt(0));
        }
        return "[" + rankStr + "\'" + SUITS[suit].toLowerCase().charAt(0) + "]";
    }

    public static String rankToString(int rank) {
        return RANKS[rank];
    }

    public short getRank() {
         return rank;
    }

    public String getSuit() {
        return SUITS[suit];
    }
}
