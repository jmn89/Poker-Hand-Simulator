package github.jmn89.pokersim.logic;
//@author jmn89

public class Main {

    public static void main(String args[]) {

        String[] sampleNames = {"Zak", "Abi", "Jim", "Baz", "Dan", "Lew", "Jon", "Kat", "Bob"};
        int playerCount;
        if (args.length == 0) {
            playerCount = 6;
        } else {
            playerCount = Integer.parseInt(args[0]);
        }

        String[] names = new String[playerCount];
        for (int i = 0; i < playerCount; i++) {
            names[i] = sampleNames[i];
        }

        Game g = new Game(names);
        g.dealNewHand();
        g.concludeHand();
    }
}
