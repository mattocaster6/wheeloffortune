package game;

/**
 * Type of guess outcome in Wheel of Fortune game
 *
 */
public enum GuessOutcomeType {
    /**
     * Player made the winning letter guess
     */
    WINNER,
    /**
     * Player made a regular letter guess (vowel or non-vowel)
     */
    NORMAL_GUESS,
    /**
     * Player tried to make a vowel guess but couldn't afford
     */
    VOWEL_CANT_AFFORD,
    /**
     * Player guessed the full phrase correctly and wins
     */
    PHRASE_GUESS_CORRECT,
    /**
     * Player guessed the full phrase incorrectly
     */
    PHRASE_GUESS_INCORRECT
}
