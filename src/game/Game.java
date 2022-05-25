package game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * This class represents the main WoF game logic
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class Game {

    // Declares class fields
    private final Wheel wheel;
    private final Phrase phrase;
    private final ArrayList<Player> playerList;
    private int playerTurn;
    private Player winner;
    private final Random rand = new Random();

    /**
     * Initializes and instantiates game objects.
     */
    public Game() {
        wheel = new Wheel();
        phrase = new Phrase();
        playerList = new ArrayList<>();
    }

    /**
     * Starts a new game by setting the first players turn (random) and setting a new phrase (random)
     */
    public void newGame() {
        phrase.setRandomPhrase();
        setRandomFirstPlayer();
    }

    /**
     * Checks guessed letter against phrase and increments player
     * score according to number of matches that are found.
     *
     * @param guess The letter being guessed
     * @return returns the outcome of the attempted letter guess.
     */
    public GuessOutcome guessLetter(String guess) {

        boolean guessIsVowel = guessIsVowel(guess);
        int wheelValue = wheel.getSelectedSegment().getValue();
        GuessOutcomeType outcomeType = GuessOutcomeType.NORMAL_GUESS;

        if (guessIsVowel) {
            if(playerCanAffordVowel(getCurrentPlayer())) {
                getCurrentPlayer().loseScore(250);
            } else {
                outcomeType = GuessOutcomeType.VOWEL_CANT_AFFORD;
                return new GuessOutcome(0, 0, outcomeType, true);
            }
        }

        int guessMatches = phrase.matchLetter(guess);
        int scoreEarned = guessMatches * wheelValue;

        getCurrentPlayer().addScore(scoreEarned);

        // If no matches are found, advance to next turn
        if (guessMatches == 0) {
            nextTurn();
        }

        // If full phrase has now been guessed, set winner.
        if (phrase.hiddenEqualsCurrent()) {
            outcomeType = GuessOutcomeType.WINNER;
            setWinner(getPlayerList().stream().max(Comparator.comparing(Player::getScore)).get());
        }

        return new GuessOutcome(guessMatches, scoreEarned, outcomeType, guessIsVowel);
    }

    /**
     * Checks guessed phrase against hidden phrase and increments current player score if matches are found,
     * ends game if correct guess.
     * @param guess the user-guessed phrase
     * @return returns the outcome of the attempted phrase guess
     */
    public GuessOutcome guessPhrase(String guess) {
        int wheelValue = wheel.getSelectedSegment().getValue();

        if (phrase.matchPhrase(guess)) {
            int missingLetters = phrase.getNumOfMissingLetters();
            int score = missingLetters * wheelValue;

            getCurrentPlayer().addScore(score);

            setWinner(getCurrentPlayer());

            return new GuessOutcome(missingLetters, score, GuessOutcomeType.PHRASE_GUESS_CORRECT);

        } else {
            nextTurn();

            return new GuessOutcome(0, 0, GuessOutcomeType.PHRASE_GUESS_INCORRECT);
        }
    }

    /**
     * Checks whether player-entered guess is a vowel
     * @param guess the player-entered guess
     * @return returns boolean value indicating whether the player entered guess was a vowel.
     */
    private boolean guessIsVowel(String guess) {
        return "AEIOU".contains(guess.toUpperCase());
    }

    /**
     * Checks whether the player can afford a vowel.
     * @param player the player to check.
     * @return returns boolean value indicating whether the player can afford a vowel.
     */
    private boolean playerCanAffordVowel(Player player) {
        return player.getScore() - 250 >= 0;
    }

    /**
     * Adds a new player
     *
     * @param player new player to add
     */
    public void addPlayer(Player player) {
        playerList.add(player);
    }

    /**
     * Resets the player list
     */
    public void resetPlayers() {
        playerList.clear();
    }

    /**
     * Sets the first player to a random player.
     */
    public void setRandomFirstPlayer() {

        int numPlayers = playerList.size();

        if(numPlayers == 1) {
            playerTurn = 0;
            return;
        }

        playerTurn = rand.nextInt(playerList.size());
    }

    /**
     * Advances to next player turn
     */
    public void nextTurn() {
        if (playerList.size() == 1) {
            return;
        }

        if (playerTurn + 1 == playerList.size()) {
            playerTurn = 0;
        } else {
            playerTurn++;
        }
    }

    //------------------ GETTERS AND SETTERS ---------------------//

    /**
     * Gets the player index of the player whose turn it is
     *
     * @return return index of current-turn player
     */
    public int getPlayerTurn() {
        return playerTurn;
    }

    /**
     * Gets the player whose turn it currently is
     * @return returns the player whose turn it currently is
     */
    public Player getCurrentPlayer() {
        return playerList.get(playerTurn);
    }

    /**
     * Gets the object representing the Wheel of Fortune
     *
     * @return return object representing Wheel of Fortune
     */
    public Wheel getWheel() {
        return wheel;
    }


    /**
     * Gets the winning Player
     *
     * @return return the winning Player
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Sets the winning Player
     *
     * @param winner winning Player
     */
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    /**
     * Gets the List containing current players
     *
     * @return return List containing current players
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Gets the current game Phrase
     *
     * @return returns the current game Phrase
     */
    public Phrase getPhrase() {
        return phrase;
    }

    /**
     * Sets the current player turn, for testing purposes
     * @param playerTurn player turn to set to
     */
    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

}
