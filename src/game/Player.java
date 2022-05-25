package game;

/**
 * This class represents a player in the Wheel of Fortune game.
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class Player {

    //Declares class fields.
    private final String name;
    private int score;

    /**
     * Initializes player values
     * @param name name of player
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * Adds an integer to the current player score
     * @param score amount to increase score by
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Subtracts an integer from the current player score
     * @param score amount to decrease score by
     */
    public void loseScore(int score) {
        this.score -= score;
    }


    //------------------ GETTERS AND SETTERS ---------------------//

    /**
     * Gets the name of the player
     * @return returns the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current score of the player
     * @return returns the current score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the current score of the player
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }
}
