package game;

/**
 * Represents the outcome of an attempted wheel of fortune guess.
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class GuessOutcome {

    public int lettersFound;
    public int score;
    public GuessOutcomeType type;
    public boolean vowel;

    /**
     * Constructs the outcome
     * @param lettersFound number of letters found
     * @param score score earned from guess
     * @param type type of outcome
     * @param vowel letter guess was a vowel
     */
    public GuessOutcome(int lettersFound, int score, GuessOutcomeType type, boolean vowel) {
        this.lettersFound = lettersFound;
        this.score = score;
        this.type = type;
        this.vowel = vowel;
    }

    /**
     * Constructs the outcome without vowel
     * @param lettersFound number of letters found
     * @param score score earned from guess
     * @param type type of outcome
     */
    public GuessOutcome(int lettersFound, int score, GuessOutcomeType type) {
        this.lettersFound = lettersFound;
        this.score = score;
        this.type = type;
        this.vowel = false;
    }

}
