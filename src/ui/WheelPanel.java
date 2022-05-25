package ui;

import game.Game;
import game.Player;
import game.SegmentType;
import game.WheelSegment;
import sound.SoundEffectPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class represents the JPanel containing Wheel related components
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class WheelPanel extends JPanel {

    //Declare fields
    private BufferedImage wheelImage;
    private Timer wheelSpinTimer;
    private final JButton spinButton;
    private final GameCard gameCard;
    private final Game game;

    Timer guessingMusicTimer;

    /**
     * Initializes WheelPanel components
     * @param gameCard GameCard object attached to WheelPanel
     * @param game Game object attached to WheelPanel
     */
    public WheelPanel(GameCard gameCard, Game game) {
        setBackground(Color.BLACK);
        setLayout(null);

        this.gameCard = gameCard;
        this.game = game;

        loadImage();

        // Initializes button to spin wheel
        spinButton = new JButton("SPIN");
        spinButton.setBounds(100, 350, 140, 70);
        spinButton.setFont(new Font("Arial", Font.BOLD, 35));
        spinButton.setBackground(Color.WHITE);
        spinButton.setFocusPainted(false);
        spinButton.addActionListener(e -> {
            spinButton.setVisible(false);
            startSpin();
        });
        add(spinButton);

        // Initializes timer for starting guessing mode
        guessingMusicTimer = new Timer(1000, e -> {
            gameCard.getGuessArea().setVisible(true);
            gameCard.getGuessField().requestFocus();
            gameCard.ui.getSE().play(SoundEffectPlayer.GUESSING_TIME);
            gameCard.ui.getSE().setVolume(0.7f);
            guessingMusicTimer.stop();
        });
    }

    /**
     * Gets the spin button
     *
     * @return returns the spin button
     */
    public JButton getSpinButton() {
        return spinButton;
    }

    /**
     * Loads the image for the WoF wheel.
     */
    private void loadImage() {
        try {
            InputStream imageStream = getClass().getResourceAsStream("/resources/images/wheel.png");
            assert imageStream != null;
            wheelImage = ImageIO.read(imageStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts spinning the Wheel of Fortune wheel using Java Timer
     */
    public void startSpin() {
        game.getWheel().startSpin();
        gameCard.ui.getSE().play(SoundEffectPlayer.WHEEL_SPIN, (int)game.getWheel().getSpinVelocity()*1000);
        gameCard.ui.getSE().setVolume(0.7f);

        wheelSpinTimer = new Timer(16, (ev) -> {
            if (game.getWheel().getSpinVelocity() <= 0) {
                wheelStopped();
                return;
            }

            game.getWheel().spin();
            repaint();
        });

        wheelSpinTimer.start();
    }

    /**
     * Runs when wheel velocity hits 0 (the wheel has stopped). Handles what should be done depending on the type of the selected segment.
     */
    private void wheelStopped() {
        WheelSegment segment = game.getWheel().getSelectedSegment();
        Player currentPlayer = game.getPlayerList().get(game.getPlayerTurn());

        gameCard.ui.getSE().stop();

        // Handles what should be done depending on segment whel stopped on
        switch (segment.getType()) {
            case BANKRUPT:
                gameCard.ui.getSE().play(SoundEffectPlayer.BAD_SPIN);
                currentPlayer.setScore(0);
                gameCard.getScoreLabels().get(game.getPlayerTurn()).setText(currentPlayer.getName() + ": £" + currentPlayer.getScore());
                JOptionPane.showMessageDialog(gameCard, "Oh no! You're bankrupt! Your score has been reset to 0 and you lose your turn!");
                game.nextTurn();
                repaint();
                spinButton.setVisible(true);
                break;
            case LOSE_A_TURN:
                gameCard.ui.getSE().play(SoundEffectPlayer.BAD_SPIN);
                game.nextTurn();
                JOptionPane.showMessageDialog(gameCard, "Oh no! You've lost your turn!");
                repaint();
                spinButton.setVisible(true);
                break;
            case FREE_SPIN:
                gameCard.ui.getSE().play(SoundEffectPlayer.GOOD_SPIN);
                JOptionPane.showMessageDialog(gameCard, "You get another spin for free!");
                spinButton.setVisible(true);
                break;
            default:
                gameCard.ui.getSE().play(SoundEffectPlayer.GOOD_SPIN);
                guessingMusicTimer.start();

        }

        wheelSpinTimer.stop();
    }

    /**
     * Paints custom graphics to WheelPanel
     *
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawWheel(g2d);
        drawTick(g2d);
        drawSelectedSegment(g2d);

        drawPlayerTurn(g2d);

        if(gameCard.ui.TESTING_MODE) {
            drawTestingArea(g2d);
        }
    }

    /**
     * Draws a tick above the wheel
     *
     * @param g2d Graphics2D object from paintComponent
     */
    private void drawTick(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 35));
        g2d.setColor(Color.WHITE);
        g2d.drawString("↓", (300 + wheelImage.getWidth(null) / 2) - 8, 140);
    }

    /**
     * Draws the wheel of fortune wheel.
     *
     * @param g2d Graphics2D object from paintComponent
     */
    private void drawWheel(Graphics2D g2d) {
        AffineTransform ar = new AffineTransform();
        ar.translate(300, 150);
        ar.rotate(Math.toRadians(game.getWheel().getCurrentAngle()), wheelImage.getWidth(null) / 2d, wheelImage.getHeight(null) / 2d);
        g2d.drawImage(wheelImage, ar, null);
    }

    /**
     * Draws the selected segment of the wheel of fortune
     *
     * @param g2d Graphics2D object from paintComponent
     */
    private void drawSelectedSegment(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 40));
        g2d.setColor(Color.WHITE);

        if (game.getWheel().getSelectedSegment().getType() == SegmentType.MONEY) {
            drawStringAtCenterHorizontally("£" + game.getWheel().getSelectedSegment().getValue(), g2d, 600);
        } else {
            drawStringAtCenterHorizontally(game.getWheel().getSelectedSegment().getTypeAsString(), g2d, 600);
        }
    }

    /**
     * Draws area for game testing
     *
     * @param g2d Graphics2D object from paintComponent
     */
    private void drawTestingArea(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 13));
        g2d.setColor(Color.WHITE);

        int xOffset = 335;
        int yOffset = 40;

        g2d.drawString("TESTING AREA", gameCard.ui.getWidth() - xOffset, yOffset);
        g2d.drawString("Players: " + game.getPlayerList().size(), gameCard.ui.getWidth() - xOffset, yOffset + 15);
        g2d.drawString("Player turn index: " + game.getPlayerTurn(), gameCard.ui.getWidth() - xOffset, yOffset + 30);
        g2d.drawString("Player turn name: " + game.getCurrentPlayer().getName(), gameCard.ui.getWidth() - xOffset, yOffset + 45);
        g2d.drawString("Hidden phrase: " + game.getPhrase().getHiddenPhrase(), gameCard.ui.getWidth() - xOffset, yOffset + 60);
        g2d.drawString("Current phrase: " + game.getPhrase().getCurrentPhrase(), gameCard.ui.getWidth() - xOffset, yOffset + 75);
        g2d.drawString("Phrase missing letters: " + game.getPhrase().getNumOfMissingLetters(), gameCard.ui.getWidth() - xOffset, yOffset + 90);
        g2d.drawString("Selected segment type: " + game.getWheel().getSelectedSegment().getTypeAsString(), gameCard.ui.getWidth() - xOffset, yOffset + 105);
        g2d.drawString("Selected segment value: " + game.getWheel().getSelectedSegment().getValue(), gameCard.ui.getWidth() - xOffset, yOffset + 120);
        g2d.drawString("Wheel angle: " + game.getWheel().getCurrentAngle(), gameCard.ui.getWidth() - xOffset, yOffset + 135);
        g2d.drawString("Wheel velocity: " + game.getWheel().getSpinVelocity(), gameCard.ui.getWidth() - xOffset, yOffset + 150);
    }

    /**
     * Draws the player whose turn it is
     *
     * @param g2d Graphics2D object from paintComponent
     */
    private void drawPlayerTurn(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 35));
        g2d.drawString("Turn: " + game.getPlayerList().get(game.getPlayerTurn()).getName(), 40, 330);
    }

    /**
     * Draws a string to screen centered horizontally
     *
     * @param string String to draw
     * @param g2d    Graphics2D object from paintComponent
     * @param y      y position of string to draw
     */
    private void drawStringAtCenterHorizontally(String string, Graphics2D g2d, int y) {
        FontMetrics fm = g2d.getFontMetrics();
        int x = (gameCard.getWidth() - fm.stringWidth(string)) / 2;
        g2d.drawString(string, x, y);
    }
}
