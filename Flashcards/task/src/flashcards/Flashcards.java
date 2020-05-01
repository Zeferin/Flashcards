package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Flashcards {

    private class CardDetails {
        String definition;
        int nrOfErrors;

        public CardDetails(String definition, int nrOfErrors) {
            this.definition = definition;
            this.nrOfErrors = nrOfErrors;
        }
    }

    private Map<String, CardDetails> cards;

    Flashcards()
    {
        cards = new LinkedHashMap<>();
    }


    public int getHardestCards(List<String> hardestCards) {
        int max = 0;
        for (Map.Entry<String, CardDetails> entry : cards.entrySet()) {
            if (entry.getValue().nrOfErrors > max)
                max = entry.getValue().nrOfErrors;
        }
        if (max > 0)
            for (Map.Entry<String, CardDetails> entry : cards.entrySet()) {
                if (entry.getValue().nrOfErrors == max) {
                    hardestCards.add(entry.getKey());
                }
            }
        return max;
    }

    public void resetErrors()
    {
        cards.forEach((k,v) -> {
            v.nrOfErrors = 0;
        });
    }

    public String getKeyByDefinition(String definition)
    {
        String key="";
        for (Map.Entry<String, CardDetails> entry : cards.entrySet()) {
            if (entry.getValue().definition.equalsIgnoreCase(definition)) {
                key = entry.getKey();
                break;
            }
        }
        return key;
    }

    public String getDefinitionByKey(String key)
    {
        CardDetails details = cards.get(key);
        return details == null ? null:details.definition;
    }

    public boolean containsKey(String key)
    {
        return cards.containsKey(key);
    }

    public int deckSize() { return cards.size();}

    public String keyAtIndex(int index)
    {
        List<String> keys = new ArrayList<String>(cards.keySet());
        return keys.get(index);
    }

    public boolean addCard(String key, String definition) {
        return addCard(key, definition, 0);
    }

    public boolean addCard(String key, String definition, int nrOfErrors) {
        return null != cards.put(key, new CardDetails(definition, nrOfErrors));
    }

    public int importCards(String fileName)
    {
        int nrOfCards = 0;
        Scanner sc = null;
        try {
            sc = new Scanner(new File(fileName));

            int size = Integer.parseInt(sc.nextLine());
            for(int i=0;i<size;i++)
            {
                String key = sc.nextLine();
                String definition = sc.nextLine();
                int nrOfErrors = Integer.parseInt(sc.nextLine());
                addCard(key, definition, nrOfErrors);
            }
            nrOfCards = size;
            sc.close();
        } catch (FileNotFoundException e) {
            nrOfCards = -1;
        }
        return nrOfCards;
    }

    public boolean exportCards(String fileName)
    {
        boolean succes = true;
        try {
            final FileWriter out = new FileWriter(new File(fileName));

            out.write(cards.size()+"\n");
            for (Map.Entry<String, CardDetails> entry : cards.entrySet()) {
                out.write(entry.getKey() + "\n");
                out.write(entry.getValue().definition + "\n");
                out.write(entry.getValue().nrOfErrors + "\n");
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            succes = false;
        }
        return succes;
    }

    public void remove(String input) {
        cards.remove(input);
    }

    public void error(String someKey) {
        cards.get(someKey).nrOfErrors++;
    }
}
