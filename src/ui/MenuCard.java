package ui;

import game.Game;
import sound.SoundEffectPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * This class represents the Menu of the game as a card in a CardLayout.
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class MenuCard extends Card {

    //Declares class fields
    private JLabel title;
    private JButton startButton;
    private JButton exitButton;
    private JLabel imageLabel;
    private JPanel menuImagePanel;

    /**
     * Initializes MenuCard
     * @param ui UI object attached to MenuCard
     * @param game Game object attached to MenuCard
     */
    public MenuCard(UIPanel ui, Game game) {
        super(ui, game);
    }

    /**
     * This method initializes the menu JPanel and adds necessary components
     */
    public void initialize() {
        super.initialize();
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        addTitle();

        addMenuPanel();

        if(ui.THEME_MUSIC_ON) {
            ui.getSE().play(SoundEffectPlayer.THEME_MUSIC);
            ui.getSE().setVolume(0.5f);
        }
    }

    /**
     * Adds title the menu screen
     */
    private void addTitle() {
        title = new JLabel("Wheel of Fortune");
        title.setFont(new Font("Arial", Font.BOLD, 75));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.PAGE_START);
    }

    /**
     * Adds logo and buttons to menu screen
     */
    private void addMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));

        // Adds Wheel of Fortune logo
        menuImagePanel = new JPanel();
        menuImagePanel.setLayout(null);
        menuImagePanel.setPreferredSize(new Dimension(ui.getWidth(), 500));
        menuImagePanel.setOpaque(false);
        try {
            BufferedImage menuImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/menu-image.png")));
            imageLabel = new JLabel(new ImageIcon(menuImage));
            imageLabel.setBounds(250, 30, 500, 500);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        menuImagePanel.add(imageLabel);

        menuPanel.add(menuImagePanel);

        // Adds menu buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 20));
        buttonPanel.setBorder(new EmptyBorder(20, 150, 20, 150));

        startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 40));
        startButton.setBackground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            ui.getSetupCard().initialize();
            ui.switchToCard(UIPanel.SETUP);
        });

        buttonPanel.add(startButton);

        exitButton = new JButton("EXIT");
        exitButton.setFont(new Font("Arial", Font.BOLD, 40));
        exitButton.setBackground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(exitButton);

        menuPanel.add(buttonPanel);
        add(menuPanel, BorderLayout.CENTER);
    }

}
