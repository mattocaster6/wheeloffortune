package ui;

import game.*;
import sound.SoundEffectPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class represents the primary game screen as a card in a CardLayout
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class GameCard extends Card {

    //Declares class fields
    private JPanel scoresPane;
    private ArrayList<JLabel> scoreLabels;
    private WheelPanel wheelPanel;
    private JPanel wordArea;
    private JTextField guessField;
    private JLabel phraseLabel;
    private JPanel guessArea;
    private JPanel guessButtonArea;
    private JButton guessLetterButton;
    private JButton guessWordButton;

    /**
     * Initializes GameCard
     * @param ui ui object attached to GameCard
     * @param game game object attached to GameCard
     */
    public GameCard(UIPanel ui, Game game) {
        super(ui, game);
    }

    /**
     * Gets the List containing labels representing player scores.
     *
     * @return returns the list containing labels representing player scores.
     */
    public ArrayList<JLabel> getScoreLabels() {
        return scoreLabels;
    }

    /**
     * Gets the text field for player guesses
     *
     * @return returns the text field for player guesses
     */
    public JTextField getGuessField() {
        return guessField;
    }

    /**
     * Gets the JPanel representing the player guess area.
     *
     * @return returns the JPanel representing the player guess area.
     */
    public JPanel getGuessArea() {
        return guessArea;
    }


    /**
     * Initializes main game screen and adds necessary components
     */
    public void initialize() {
        super.initialize();
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        initializeScoresPane();

        wheelPanel = new WheelPanel(this, game);
        add(wheelPanel, BorderLayout.CENTER);

        initializeWordArea();
    }


    /**
     * Initializes the panel responsible for handling the phrase guessing.
     */
    private void initializeWordArea() {
        // Initializes panel containing phrase guessing content
        wordArea = new JPanel();
        wordArea.setBackground(Color.BLACK);
        wordArea.setLayout(new GridLayout(1, 2));
        add(wordArea, BorderLayout.SOUTH);

        // Initializes panel containing guessing interface
        guessArea = new JPanel();
        guessArea.setLayout(new GridLayout(2, 1, 0, 5));
        guessArea.setBackground(Color.BLACK);

        // Initializes panel containing guess buttons
        guessButtonArea = new JPanel();
        guessButtonArea.setLayout(new GridLayout(1, 2));

        // Initializes text field for guess input
        guessField = new JTextField();
        guessField.setFont(new Font("Arial", Font.PLAIN, 30));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessArea.add(guessField);

        // Initializes button for guessing single letter
        guessLetterButton = new JButton("Guess Letter");
        guessLetterButton.setBackground(Color.WHITE);
        guessLetterButton.setFocusPainted(false);
        guessLetterButton.addActionListener(e -> guessLetter());

        // Initializes button for guessing whole phrase
        guessWordButton = new JButton("Guess Full Phrase");
        guessWordButton.setBackground(Color.WHITE);
        guessWordButton.setFocusPainted(false);
        guessWordButton.addActionListener(e -> guessPhrase());

        guessButtonArea.add(guessLetterButton);
        guessButtonArea.add(guessWordButton);

        guessArea.add(guessButtonArea);

        // Initializes label containing current state of phrase <html> used to enable line-wrapping
        phraseLabel = new JLabel("<html>" + game.getPhrase().getCurrentPhrase() + "</html>");
        phraseLabel.setFont(new Font("Arial", Font.BOLD, 30));
        phraseLabel.setForeground(Color.WHITE);
        phraseLabel.setHorizontalAlignment(SwingConstants.CENTER);

        wordArea.add(guessArea);
        wordArea.add(phraseLabel);

        guessArea.setVisible(false);
    }

    /**
     * Initializes the panel containing player scores.
     */
    private void initializeScoresPane() {
        // Initializes list containing players
        scoreLabels = new ArrayList<>();

        // Initializes panel containing player names and scores
        scoresPane = new JPanel();
        scoresPane.setBorder(new EmptyBorder(20, 0, 0, 0));
        scoresPane.setLayout(new GridLayout(1, game.getPlayerList().size()));
        scoresPane.setBackground(Color.BLACK);

        // Adds player name/score labels
        for (Player player : game.getPlayerList()) {
            JLabel playerLabel = new JLabel(player.getName() + ": £" + player.getScore());
            playerLabel.setHorizontalAlignment(JLabel.CENTER);
            playerLabel.setVerticalAlignment(JLabel.CENTER);
            playerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            playerLabel.setForeground(Color.WHITE);
            scoreLabels.add(playerLabel);
            scoresPane.add(playerLabel);
        }

        add(scoresPane, BorderLayout.NORTH);
    }

    /**
     * Initiates a letter guess attempt using Wheel of Fortune game logic
     */
    private void guessLetter() {

        // Performs validation checks
        if (!guessLetterValid()) return;

        // Sets variables needed
        int currentPlayerIndex = game.getPlayerTurn();
        String currentPlayerName = game.getCurrentPlayer().getName();
        String guess = guessField.getText();
        Phrase phrase = game.getPhrase();
        SoundEffectPlayer se = ui.getSE();

        // Notifies game of guess and stores GuessOutcome response from game
        GuessOutcome outcome = game.guessLetter(guess);

        if(outcome.vowel) {
            if(outcome.type != GuessOutcomeType.VOWEL_CANT_AFFORD) {
                JOptionPane.showMessageDialog(this, "You purchased a vowel for 250 score");
            } else {
                JOptionPane.showMessageDialog(this, "You need 250 score to purchase a vowel!");
                return;
            }
        }

        se.stop();

        int guessMatches = outcome.lettersFound;
        int scoreEarned = outcome.score;

        guessField.setText("");

        int currentPlayerScore = game.getPlayerList().get(currentPlayerIndex).getScore();
        scoreLabels.get(currentPlayerIndex).setText(currentPlayerName + ": £" + currentPlayerScore);
        phraseLabel.setText("<html>" + phrase.getCurrentPhrase() + "</html>");

        if (guessMatches == 0) {
            se.play(SoundEffectPlayer.GUESS_INCORRECT);
            JOptionPane.showMessageDialog(this, "You did not find any letters or that letter was already found, play passes to next player");
        } else {
            se.play(SoundEffectPlayer.GUESS_CORRECT);
            JOptionPane.showMessageDialog(this, "You found " + guessMatches + " letters, multiplied by the wheel value this nets you " + scoreEarned + " score!");
        }

        wheelPanel.repaint();
        guessArea.setVisible(false);

        wheelPanel.getSpinButton().setVisible(true);

        // If full phrase has been revealed, set winner and end game
        if (outcome.type == GuessOutcomeType.WINNER) {
            ui.getEndCard().initialize();
            ui.switchToCard(UIPanel.END);
        }
    }


    /**
     * Checks that the user entered guess meets all validation rules (guess must be 1 letter)
     * @return returns boolean value indicating whether the entered guess meets all validation rules.
     */
    private boolean guessLetterValid() {
        if (guessField.getText().length() > 1) {
            JOptionPane.showMessageDialog(this, "You must only enter 1 letter!");
            return false;
        }

        if (guessField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "You have to enter something!");
            return false;
        }
        return true;
    }

    /**
     * Initiates a phrase guess attempt using Wheel of Fortune game logic.
     */
    private void guessPhrase() {
        String guess = guessField.getText();
        SoundEffectPlayer se = ui.getSE();

        // If user hasn't entered anything
        if (guess.equals("")) {
            JOptionPane.showMessageDialog(this, "You need to enter something!");
            return;
        }

        se.stop();

        // Notifies game of guess and stores GuessOutcome response from game
        GuessOutcome outcome = game.guessPhrase(guess);

        if (outcome.type == GuessOutcomeType.PHRASE_GUESS_CORRECT) {
            int score = outcome.score;

            se.play(SoundEffectPlayer.SOLVED);

            JOptionPane.showMessageDialog(this, "Congratulations, you guessed the word correctly and earned " + score + " points!");

            ui.getEndCard().initialize();
            ui.switchToCard(UIPanel.END);
        } else if (outcome.type == GuessOutcomeType.PHRASE_GUESS_INCORRECT) {
            ui.getSE().play(SoundEffectPlayer.GUESS_INCORRECT);
            guessField.setText("");
            JOptionPane.showMessageDialog(this, "Sorry, you guessed wrong. Play passes to next player.");
            guessArea.setVisible(false);
            wheelPanel.getSpinButton().setVisible(true);
            wheelPanel.repaint();
        }
    }

}
