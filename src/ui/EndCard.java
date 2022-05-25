package ui;

import game.Game;
import sound.SoundEffectPlayer;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the game end screen as a card in a CardLayout
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class EndCard extends Card {

    //Declares class fields
    private JLabel winnerNameLabel;
    private JLabel winnerScoreLabel;
    private JButton endGameButton;
    private JPanel congratulationsPanel;
    private Timer animationTimer;
    private ConfettiPiece[] confetti;
    private JPanel endGameButtonPanel;
    private JLabel phraseRevealLabel;
    private JLabel phraseLabel;

    /**
     * Initializes EndCard
     * @param ui ui object attached to EndCard
     * @param game game object attached to EndCard
     */
    public EndCard(UIPanel ui, Game game) {
        super(ui, game);
    }

    /**
     * Initializes End screen JPanel and adds necessary components
     */
    public void initialize() {
        super.initialize();

        ui.getSE().play(SoundEffectPlayer.CONGRATS);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // Initializes main congratulations panel
        congratulationsPanel = new JPanel();
        congratulationsPanel.setOpaque(false);
        congratulationsPanel.setLayout(new GridLayout(5, 1));

        // Initializes label declaring name of winner
        winnerNameLabel = new JLabel();
        winnerNameLabel.setText("Congratulations: " + game.getWinner().getName() + "!");
        winnerNameLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        winnerNameLabel.setForeground(Color.WHITE);
        winnerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        congratulationsPanel.add(winnerNameLabel);

        // Initializes label declaring score of player
        winnerScoreLabel = new JLabel();
        winnerScoreLabel.setText("You won £" + game.getWinner().getScore());
        winnerScoreLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        winnerScoreLabel.setForeground(Color.WHITE);
        winnerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        congratulationsPanel.add(winnerScoreLabel);

        // Initializes label introducing hidden phrase
        phraseRevealLabel = new JLabel();
        phraseRevealLabel.setText("The hidden phrase was:");
        phraseRevealLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        phraseRevealLabel.setForeground(Color.WHITE);
        phraseRevealLabel.setHorizontalAlignment(SwingConstants.CENTER);
        congratulationsPanel.add(phraseRevealLabel);

        // Initializes label revealing hidden phrase
        phraseLabel = new JLabel();
        phraseLabel.setText(game.getPhrase().getHiddenPhrase());
        phraseLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        phraseLabel.setForeground(Color.WHITE);
        phraseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        congratulationsPanel.add(phraseLabel);

        // Initializes panel containing button to end game
        endGameButtonPanel = new JPanel(new FlowLayout());
        endGameButtonPanel.setOpaque(false);
        endGameButton = new JButton("END GAME");
        endGameButton.setFont(new Font("Arial", Font.BOLD, 50));
        endGameButton.setBackground(Color.WHITE);
        endGameButton.setFocusPainted(false);

        endGameButton.addActionListener(e -> {
            // Stops confetti animation timer
            animationTimer.stop();
            ui.getMenuCard().initialize();
            ui.switchToCard(UIPanel.MENU);
        });
        endGameButtonPanel.add(endGameButton);
        congratulationsPanel.add(endGameButtonPanel);

        add(congratulationsPanel, BorderLayout.CENTER);

        // Initializes new randomized confetti pieces
        createNewConfetti();

        // Starts confetti animation timer
        animationTimer = new Timer(16, e -> {
            updateAllConfetti();
            repaint();
        });
        animationTimer.start();

    }

    /**
     * Creates an array of new, randomized confetti pieces.
     */
    private void createNewConfetti() {
        confetti = new ConfettiPiece[200];

        for(int i = 0; i < confetti.length; i++) {
            confetti[i] = new ConfettiPiece();
            System.out.println("Confetti added: " + confetti[i].x);
        }
    }

    /**
     * Updates position and angle of all confetti on the screen.
     */
    private void updateAllConfetti() {
        for(ConfettiPiece piece : confetti) {
            piece.update();
        }
    }

    /**
     * Paints to screen
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        drawConfetti(g2d);
    }

    /**
     * Paints all confetti pieces to screen
     * @param g2d Graphics2D object passed in from paintComponent
     */
    public void drawConfetti(Graphics2D g2d) {
        for(ConfettiPiece piece : confetti) {
            piece.draw(g2d);
        }
    }
}
