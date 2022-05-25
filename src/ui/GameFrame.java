package ui;

import game.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * This class represents the main JFrame
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class GameFrame extends JFrame {

    //Declares class fields
    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 900;
    private final String FRAME_TITLE = "Wheel of Fortune";
    private final String ICON_PATH = "/resources/images/icon.png";
    private Game game;
    private UIPanel ui;

    /**
     * Initializes GameFrame
     */
    public GameFrame() {
        initialize();
    }

    /**
     * Main method of entry to program, instantiates GameFrame, uses Swing worker thread.
     *
     * @param args main method arguments, not relevant to this program
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame();
            gameFrame.setVisible(true);
        });
    }

    /**
     * This method initializes JFrame attributes and adds UIPanel.
     */
    private void initialize() {
        game = new Game();

        ui = new UIPanel(game);

        setIcon();
        ui.getMenuCard().initialize();
        add(ui);

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(FRAME_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Loads icon from resources and sets this as icon image for JFrame
     */
    private void setIcon() {
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(ICON_PATH))));
            setIconImage(img.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
