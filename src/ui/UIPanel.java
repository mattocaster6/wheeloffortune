package ui;

import game.Game;
import sound.SoundEffectPlayer;

import javax.swing.*;
import java.awt.*;
/**
 * This class represents the main UI interface using a CardLayout for easy screen switching
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class UIPanel extends JPanel {

    //Declares class fields
    public final static String MENU = "MenuCard";
    public final static String SETUP = "SetupCard";
    public final static String GAME = "GameCard";
    public final static String END = "EndCard";

    private MenuCard menuCard;
    private SetupCard setupCard;
    private GameCard gameCard;
    private EndCard endCard;
    private final Game game;
    private final SoundEffectPlayer se;

    // Theme music enabled or not
    public final boolean THEME_MUSIC_ON = true;

    // Testing mode: Enables testing area on game screen displaying some relevant variables
    public final boolean TESTING_MODE = true;

    /**
     * Initializes UIPanel components
     * @param game Game object attached to UIPanel
     */
    public UIPanel(Game game) {
        this.game = game;
        this.se = new SoundEffectPlayer();
        initialize();
    }

    /**
     * Gets the JPanel representing the menu screen
     *
     * @return returns the JPanel representing the menu screen
     */
    public MenuCard getMenuCard() {
        return menuCard;
    }


    /**
     * Gets the JPanel representing the setup screen
     *
     * @return returns the JPanel representing the setup screen
     */
    public SetupCard getSetupCard() {
        return setupCard;
    }

    /**
     * Gets the JPanel representing the main game screen.
     *
     * @return return main game screen JPanel.
     */
    public GameCard getGameCard() {
        return gameCard;
    }

    /**
     * Gets the JPanel representing the end game screen.
     *
     * @return return the end game screen JPanel
     */
    public EndCard getEndCard() {
        return endCard;
    }

    /**
     * Gets the sound effect player
     * @return returns the sound effect player
     */
    public SoundEffectPlayer getSE() {
        return se;
    }

    /**
     * Initializes the main UIPanel with CardLayout and necessary cards/screens/JPanels.
     */
    private void initialize() {
        CardLayout cl = new CardLayout();
        setLayout(cl);

        menuCard = new MenuCard(this, game);
        setupCard = new SetupCard(this, game);
        gameCard = new GameCard(this, game);
        endCard = new EndCard(this, game);

        add(menuCard, MENU);
        add(setupCard, SETUP);
        add(gameCard, GAME);
        add(endCard, END);

        cl.show(this, MENU);
    }

    /**
     * Switches to new card
     *
     * @param cardName Name of the card to switch to
     */
    public void switchToCard(String cardName) {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, cardName);
    }

}
