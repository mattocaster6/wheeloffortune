package ui;

import game.Game;

import javax.swing.*;

/**
 * Class representing the abstract implementation of an individual game screen in the Wof game.
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public abstract class Card extends JPanel {

    //Declares class fields
    protected UIPanel ui;
    protected Game game;

    /**
     * Initializes card components
     * @param ui UI object attached to Card
     * @param game Game object attached to Card
     */
    public Card(UIPanel ui, Game game) {
        this.ui = ui;
        this.game = game;
    }

    /**
     * Resets and initializes the card, and adds necessary components.
     */
    public void initialize() {
        removeAll();
    }
}
