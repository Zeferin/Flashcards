package flashcards;

import java.util.List;

public class UI {

    public static String mainMenu()
    {
        String text = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";
        System.out.println(text);
        return text;
    }

    public static String askForCard()
    {
        String text = "The card:";
        System.out.println(text);
        return text;
    }

    public static String askForCardDefinition()
    {
        String text = "The definition of the card:";
        System.out.println(text);
        return text;
    }

    public static String cardAdded(String card, String cardDefinition)
    {
        String text = String.format("The pair (\"%s\":\"%s\") has been added", card, cardDefinition);
        System.out.println(text);
        return text;
    }

    public static String cardAlreadyExists(String card)
    {
        String text = String.format("The card \"%s\" already exists.", card);
        System.out.println(text);
        return text;
    }

    public static String definitionAlreadyExists(String definition)
    {
        String text = String.format("The definition \"%s\" already exists.", definition);
        System.out.println(text);
        return text;
    }

    public static String cardRemoved()
    {
        String text = "The card has been removed.";
        System.out.println(text);
        return text;
    }

    public static String noSuchCard(String card)
    {
        final String text = String.format("Can't remove \"%s\": there is no such card.", card);
        System.out.println(text);
        return text;
    }

    public static String exit()
    {
        final String text = "Bye bye!";
        System.out.println(text);
        return text;
    }

    public static String askForFilename()
    {
        final String text = "File name:";
        System.out.println(text);
        return text;
    }

    public static String cardsSaved(int nr)
    {
        final String text = String.format("%d cards have been saved.", nr);
        System.out.println(text);
        return text;
    }

    public static String cardsLoaded(int nr)
    {
        final String text = String.format("%d cards have been loaded.", nr);
        System.out.println(text);
        return text;
    }

    public static String askHowManyTimes()
    {
        final String text = "How many times to ask?";
        System.out.println(text);
        return text;
    }

    public static String wrongAnswer(String right)
    {
        final String text = String.format("Wrong answer. The correct one is \"%s\".", right);
        System.out.println(text);
        return text;
    }

    public static String wrongAnswer(String right, String wrong)
    {
        final String text = String.format("Wrong answer. The correct one is \"%s\", you've just written the definition of \"%s\".", right, wrong);
        System.out.println(text);
        return text;
    }

    public static String correctAnswer()
    {
        final String text = "Correct answer";
        System.out.println(text);
        return text;
    }

    public static String printDefinitionOf(String card) {
        final String text = String.format("Print the definition of \"%s\":",card);
        System.out.println(text);
        return text;
    }

    public static String fileNotFound() {
        final String text = "File not found.";
        System.out.println(text);
        return text;
    }

    public static String notEnoughSpaceOnDisk() {
        final String text = "Not enough space on disk.";
        System.out.println(text);
        return text;
    }

    public static String logSaved() {
        final String text = "The log has been saved";
        System.out.println(text);
        return text;
    }

    public static String noHardest() {
        final String text = "There are no cards with errors.";
        System.out.println(text);
        return text;
    }

    public static String statsReset() {
        final String text = "Card statistics has been reset.";
        System.out.println(text);
        return text;
    }

    public static String hardestCard(List<String> cards, int nrOfErrors) {
        String verb = cards.size() == 1 ? "is":"are";
        String noun = cards.size() == 1 ? "card":"cards";
        String text = String.format("The hardest %s %s ", noun, verb);
        int i;
        for(i=0;i<cards.size()-1;i++)
        {
            text = String.format("%s\"%s\",", text, cards.get(i));
        }
        text = String.format("%s\"%s\". You have %d errors answering them.", text, cards.get(i), nrOfErrors);

        System.out.println(text);
        return text;
    }
}
