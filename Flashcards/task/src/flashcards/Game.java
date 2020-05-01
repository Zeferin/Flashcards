package flashcards;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private enum GameState {
        ASK {
            @Override
            void moveOn(Game game, String input) {
                game.ask();
            }
        },
        GUESS {
            @Override
            void moveOn(Game game, String input) {
                game.guess(input);
            }
        },
        CHOOSING_ACTION {
            @Override
            void moveOn(Game game, String input) {
                game.actionChosen(input);
            }
        },
        CHOOSING_CARD {
            @Override
            void moveOn(Game game, String input) {
                game.cardChosen(input);
            }
        },
        CHOOSING_CARD_DEFINITION {
            @Override
            void moveOn(Game game, String input) {
                game.cardDefinitionChosen(input);
            }
        },
        EXIT {
            @Override
            void moveOn(Game game, String input) {
                game.exit();
            }
        },
        CHOOSING_EXPORT_FILENAME {
            @Override
            void moveOn(Game game, String input) {
                game.exportToFile(input);
            }
        },
        CHOOSING_IMPORT_FILENAME {
            @Override
            void moveOn(Game game, String input) {
                game.importFromFile(input);
            }
        },
        CHOOSING_CARD_TO_REMOVE {
            @Override
            void moveOn(Game game, String input) {
                game.cardToRemoveChosen(input);
            }
        },
        LOG {
            @Override
            void moveOn(Game game, String input) {
                game.log(input);
            }
        },
        HARDEST_CARD {
            @Override
            void moveOn(Game game, String input) {
                game.hardestCard();
            }
        },
        RESET {
            @Override
            void moveOn(Game game, String input) {
                game.resetStats();
            }
        },
        CHOOSING_HOW_MANY_TIMES {
            @Override
            void moveOn(Game game, String input) {
                game.howManyTimesChosen(input);
            }
        };

        abstract void moveOn(Game game, String input);
    }

    private Flashcards cards;
    private GameState state;
    private int howManyTimesToAsk;
    private String someKey;
    private Random rand;
    private List<String> log = new ArrayList<String>();
    private String exportFile;

    private void actionChosen(String input)
    {
        switch(input)
        {
            case "ask":
                state = GameState.CHOOSING_HOW_MANY_TIMES;
                log.add(UI.askHowManyTimes());
                break;
            case "import":
                state = GameState.CHOOSING_IMPORT_FILENAME;
                log.add(UI.askForFilename());
                break;
            case "add":
                state = GameState.CHOOSING_CARD;
                log.add(UI.askForCard());
                break;
            case "export":
                state = GameState.CHOOSING_EXPORT_FILENAME;
                log.add(UI.askForFilename());
                break;
            case "remove":
                state = GameState.CHOOSING_CARD_TO_REMOVE;
                log.add(UI.askForCard());
                break;
            case "exit":
                state = GameState.EXIT;
                state.moveOn(this,"");
                break;
            case "log":
                state = GameState.LOG;
                log.add(UI.askForFilename());
                break;
            case "hardest card":
                state = GameState.HARDEST_CARD;
                state.moveOn(this,"");
                break;
            case "reset stats":
                state = GameState.RESET;
                state.moveOn(this, "");
                break;
        }
    }

    private void resetStats() {
        cards.resetErrors();
        log.add(UI.statsReset());
        state = GameState.CHOOSING_ACTION;
        log.add(UI.mainMenu());
    }

    private void hardestCard() {

        List<String> hardestCards = new ArrayList<String>();
        int nrOfErrors = cards.getHardestCards(hardestCards);
        if (hardestCards.isEmpty())
        {
            log.add(UI.noHardest());
        }
        else
        {
            log.add(UI.hardestCard(hardestCards, nrOfErrors));
        }
        state = GameState.CHOOSING_ACTION;
        log.add(UI.mainMenu());
    }

    private void log(String input) {
        try {
            FileWriter out = new FileWriter(input);

            for (String s : log) {
                out.write(s + "\n");
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.add(UI.logSaved());
        state = GameState.CHOOSING_ACTION;
        log.add(UI.mainMenu());
    }

    private void importFromFile(String input) {

        int result = cards.importCards(input);

        if (result == -1)
        {
            log.add(UI.fileNotFound());
        }
        else
        {
            log.add(UI.cardsLoaded(result));
        }

        state = GameState.CHOOSING_ACTION;
        log.add(UI.mainMenu());
    }

    private void exportToFile(String input) {


        if (cards.exportCards(input) == false)
        {
            log.add(UI.notEnoughSpaceOnDisk());
        }
        else
        {
            log.add(UI.cardsSaved(cards.deckSize()));
        }

        if (state != GameState.EXIT) {
            state = GameState.CHOOSING_ACTION;
            log.add(UI.mainMenu());
        }
    }

    private void cardDefinitionChosen(String input) {
        if (cards.getKeyByDefinition(input).isEmpty() == false)
        {
            log.add(UI.definitionAlreadyExists(input));
        }
        else
        {
            cards.addCard(someKey, input);
            log.add(UI.cardAdded(someKey, input));
        }
        state = GameState.CHOOSING_ACTION;
        log.add(UI.mainMenu());
    }

    private void cardChosen(String input) {

        if (cards.getDefinitionByKey(input) != null)
        {
            log.add(UI.cardAlreadyExists(input));
            state = GameState.CHOOSING_ACTION;
            log.add(UI.mainMenu());
        }
        else
        {
            state = GameState.CHOOSING_CARD_DEFINITION;
            log.add(UI.askForCardDefinition());
            this.someKey = input;
        }
    }

    private void howManyTimesChosen(String input) {
        howManyTimesToAsk = Integer.parseInt(input);
        state = GameState.ASK;
        state.moveOn(this,"");
    }

    private void cardToRemoveChosen(String input) {

        if (cards.getDefinitionByKey(input) == null)
        {
            log.add(UI.noSuchCard(input));
        }
        else
        {
            cards.remove(input);
            log.add(UI.cardRemoved());
        }
        state = GameState.CHOOSING_ACTION;
        log.add(UI.mainMenu());
    }

    private void ask() {

        int index = Math.abs(rand.nextInt())%cards.deckSize();
        someKey = cards.keyAtIndex(index);

        state = GameState.GUESS;
        log.add(UI.printDefinitionOf(someKey));
    }

    private void guess(String input) {

        if (input.equals(cards.getDefinitionByKey(someKey)))
        {
            log.add(UI.correctAnswer());
        }
        else
        {
            String anotherKey = cards.getKeyByDefinition(input);
            if (anotherKey.isEmpty())
                log.add(UI.wrongAnswer(cards.getDefinitionByKey(someKey)));
            else
                log.add(UI.wrongAnswer(cards.getDefinitionByKey(someKey), anotherKey));
            cards.error(someKey);
        }

        howManyTimesToAsk--;
        if(howManyTimesToAsk == 0)
        {
            log.add(UI.mainMenu());
            state = GameState.CHOOSING_ACTION;
        }
        else
        {
            state = GameState.ASK;
            state.moveOn(this,"");
        }
    }

    public Game(String importFile, String exportFile)
    {
        this.cards = new Flashcards();
        this.exportFile = exportFile;
        howManyTimesToAsk = 1;
        rand = new Random();
        if (importFile.isEmpty())
        {
            state = GameState.CHOOSING_ACTION;
            log.add(UI.mainMenu());
        }
        else
            importFromFile(importFile);
    }

    public boolean process(String action)
    {
        log.add(action);
        state.moveOn(this, action);
        return state!=GameState.EXIT;
    }

    public void exit()
    {
        log.add(UI.exit());
        if(exportFile.isEmpty() == false)
            exportToFile(exportFile);
    }
}
