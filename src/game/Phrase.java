package game;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a phrase to be used in Wheel of Fortune game
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class Phrase {

    //Declares class fields
    // Hidden phrase: original, unaltered phrase.
    private String hiddenPhrase;
    // Current phrase: current game state of phrase with underscores representing missing letters.
    private String currentPhrase;
    private final ArrayList<String> phraseList;
    private final Random rand = new Random();
    private final FileHandler fileHandler = new FileHandler("/resources/phrases.txt");

    /**
     * Initializes and instantiates phrase objects from text file
     */
    public Phrase() {
        phraseList = fileHandler.readLines();
        setRandomPhrase();
    }

    /**
     * Generates the string to display to player by replacing all letters with underscores
     */
    private void generateInitialPhrase() {
        currentPhrase = "";
        for (int i = 0; i < hiddenPhrase.length(); i++) {
            if (hiddenPhrase.charAt(i) == ' ') {
                currentPhrase += " ";
            } else {
                currentPhrase += "_";
            }
        }
    }

    /**
     * Picks a random phrase from phrase list and sets this as new phrase
     */
    public void setRandomPhrase() {
        hiddenPhrase = phraseList.get(rand.nextInt(phraseList.size()));
        generateInitialPhrase();
    }

    /**
     * Matches player input against hidden phrase
     *
     * @param c player letter guess
     * @return returns the number of matches found.
     */
    public int matchLetter(String c) {
        String[] hiddenPhraseArray = hiddenPhrase.split("");
        String[] currentPhraseArray = currentPhrase.split("");

        int matchCount = 0;

        for (int i = 0; i < hiddenPhraseArray.length; i++) {
            if (hiddenPhraseArray[i].equalsIgnoreCase(c) && currentPhraseArray[i].equals("_")) {
                matchCount++;
                currentPhraseArray[i] = hiddenPhraseArray[i];
            }
        }

        currentPhrase = "";

        for (String l : currentPhraseArray) {
            currentPhrase += l;
        }

        return matchCount;
    }

    /**
     * Gets the number of letters missing from the current game phrase
     * @return returns the number of letters missing from the current game phrase
     */
    public int getNumOfMissingLetters() {
        int num = 0;

        for (int i = 0; i < currentPhrase.length(); i++) {
            if (currentPhrase.charAt(i) == '_') {
                num += 1;
            }
        }

        return num;
    }

    /**
     * Matches player phrase guess against hidden phrase
     *
     * @param phrase player phrase guess
     * @return returns boolean value representing whether the guess was correct.
     */
    public boolean matchPhrase(String phrase) {
        return phrase.equalsIgnoreCase(hiddenPhrase);
    }

    /**
     * Checks to see if the current state of the game phrase equals the hidden, final phrase.
     *
     * @return returns boolean value indicating whether the current state of the game phrase equals the hidden, final phrase
     */
    public boolean hiddenEqualsCurrent() {
        return hiddenPhrase.equalsIgnoreCase(currentPhrase);
    }

    //------------------ GETTERS AND SETTERS ---------------------//

    /**
     * Gets the current state of the phrase including guesses by player
     *
     * @return returns the current state of the phrase including guesses by player
     */
    public String getCurrentPhrase() {
        return currentPhrase;
    }

    /**
     * Gets the string representing the hidden WoF phrase
     *
     * @return returns the string representing the hidden WoF phrase
     */
    public String getHiddenPhrase() {
        return hiddenPhrase;
    }

    /**
     * Sets hidden phrase for testing purposes.
     *
     * @param phrase String to set as phrase for testing purposes.
     */
    public void setPhrase(String phrase) {
        hiddenPhrase = phrase;
        generateInitialPhrase();
    }

    /**
     * Sets current phrase for testing purposes
     *
     * @param phrase String to set as currentPhrase for testing purposes
     */
    public void setCurrentPhrase(String phrase) {
        currentPhrase = phrase;
    }

}
