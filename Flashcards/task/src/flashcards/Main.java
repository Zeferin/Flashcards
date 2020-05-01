package flashcards;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String importFile="", exportFile="";

        for(int i=0;i<args.length;i++)
            if (args[i].equals("-import"))
                importFile = args[i+1];
            else if(args[i].equals("-export"))
                exportFile = args[i+1];

        Game flashcards = new Game(importFile, exportFile);
        boolean running = true;

        while (running)
        {
            String action = sc.nextLine();
            running = flashcards.process(action);
        }
    }
}
