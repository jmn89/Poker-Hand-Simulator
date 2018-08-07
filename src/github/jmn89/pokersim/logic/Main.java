package github.jmn89.pokersim.logic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//@author jmn89

public class Main {

    public static void main(String args[]) {

        int playerCount = 6;
        if (args.length != 0 && Integer.parseInt(args[0]) > 0 && Integer.parseInt(args[0]) < 10) {
            playerCount = Integer.parseInt(args[0]);
        }

        List<String> sampleNames = new ArrayList<>(Arrays.asList(
                "Zak", "Abi", "Jim", "Baz", "Dan", "Lew", "Jon", "Kat", "Bob"));
        sampleNames = sampleNames.subList(0, playerCount);

        Game g = new Game(sampleNames);
        g.dealNewHand();
        g.concludeHand();
    }
}
