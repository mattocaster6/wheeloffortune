package ui;

import game.Game;
import game.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

/**
 * This class represents the game setup screen as a card in a CardLayout
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class SetupCard extends Card {

    //Declares class fields
    private JLabel cardTitle;
    private JComboBox<String> numPlayersCombo;
    private JLabel numPlayersLabel;
    private JButton startGameButton;
    private JPanel optionsPanel;
    private JPanel numPlayersComboPanel;

    /**
     * Initializes SetupCard
     * @param ui UI object attached to SetupCard
     * @param game Game object attached to SetupCard
     */
    public SetupCard(UIPanel ui, Game game) {
        super(ui, game);
    }

    /**
     * Initializes the setup screen and adds necessary components
     */
    public void initialize() {
        super.initialize();
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        // Initializes label containing card title
        cardTitle = new JLabel("Game Setup");
        cardTitle.setFont(new Font("Arial", Font.BOLD, 45));
        cardTitle.setForeground(Color.WHITE);
        cardTitle.setHorizontalAlignment(JLabel.CENTER);
        cardTitle.setBorder(new EmptyBorder(20, 0, 0, 0));
        add(cardTitle, BorderLayout.PAGE_START);

        // Initializes panel containing game setup options
        optionsPanel = new JPanel(new GridLayout(3, 1, 0, 20));

        // Initializes label prompting user to enter number of players
        numPlayersLabel = new JLabel("Number of players: ");
        numPlayersLabel.setFont(new Font("Arial", Font.BOLD, 35));
        numPlayersLabel.setForeground(Color.WHITE);
        numPlayersLabel.setHorizontalAlignment(JLabel.CENTER);
        optionsPanel.add(numPlayersLabel);

        // Initializes panel to contain combo box for player number option
        numPlayersComboPanel = new JPanel();
        numPlayersComboPanel.setOpaque(false);
        numPlayersComboPanel.setLayout(new FlowLayout());

        // Initializes combo box for user to choose number of players in game
        numPlayersCombo = new JComboBox<>(new String[]{"1", "2", "3"});
        numPlayersCombo.setBackground(Color.WHITE);
        numPlayersCombo.setFont(new Font("Arial", Font.BOLD, 40));
        ((JLabel) numPlayersCombo.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        numPlayersCombo.setPreferredSize(new Dimension(100, 150));
        numPlayersCombo.setFocusable(false);

        numPlayersComboPanel.add(numPlayersCombo);
        optionsPanel.add(numPlayersComboPanel);

        // Initializes button to start new Wheel of Fortune game
        startGameButton = new JButton("Start Game");
        startGameButton.setBackground(Color.WHITE);
        startGameButton.setFont(new Font("Arial", Font.BOLD, 30));
        startGameButton.addActionListener(e -> start());

        optionsPanel.add(startGameButton);
        optionsPanel.setBackground(Color.BLACK);
        optionsPanel.setBorder(new EmptyBorder(100, 200, 100, 200));

        add(optionsPanel, BorderLayout.CENTER);
    }

    /**
     * Sets player names then starts game.
     */
    private void start() {
        game.resetPlayers();

        // Prompts user for player names and adds to list of game players
        for (int i = 0; i < Integer.parseInt(Objects.requireNonNull(numPlayersCombo.getSelectedItem()).toString()); i++) {
            String playerName = JOptionPane.showInputDialog("Enter name of player " + (i + 1) + ": ");

            if (playerName == null || playerName.equals("")) {
                return;
            }

            game.addPlayer(new Player(playerName));
        }

        if(ui.THEME_MUSIC_ON) {
            ui.getSE().stop();
        }

        game.newGame();
        ui.getGameCard().initialize();
        ui.switchToCard(UIPanel.GAME);
    }


}
