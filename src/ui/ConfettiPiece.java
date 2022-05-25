package ui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * This class represents a confetti piece to be animated on the congratulations/end screen
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class ConfettiPiece extends Rectangle {

    //Declares class fields
    private final Random rand = new Random();
    private double angle;
    private final double spinVelocity;
    private final double yVelocity;
    private final double xVelocity;
    private final Color color;

    /**
     * Instantiates a new randomized confetti piece
     */
    public ConfettiPiece() {
        angle = rand.nextDouble() * 360;
        spinVelocity = (3 + rand.nextDouble() * 5) * (rand.nextBoolean() ? 1 : -1);
        height = rand.nextInt(15);
        width = rand.nextInt(15);
        color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        xVelocity = (2 + rand.nextDouble() * 4) * (rand.nextBoolean() ? 1 : -1);
        yVelocity = 6 + rand.nextDouble() * 4;
        x = rand.nextInt(GameFrame.FRAME_WIDTH);
        y = (int) (0 - rand.nextDouble() * GameFrame.FRAME_HEIGHT);
    }

    /**
     * Updates the position and angle of the confetti piece according to its velocities.
     */
    public void update() {
        if(y <= GameFrame.FRAME_HEIGHT+20) {
            y += yVelocity;
        } else {
            y = -20;
        }

        if(x > GameFrame.FRAME_WIDTH+20) {
            x = -20;
        } else if (x < -20) {
            x = GameFrame.FRAME_WIDTH + 20;
        } else {
            x += xVelocity;
        }

        if((angle + spinVelocity) > 360) {
            angle = (angle + spinVelocity) - 360;
        } else if((angle + spinVelocity) < 0){
            angle  = 360 + angle + spinVelocity;
        } else {
            angle += spinVelocity;
        }
    }

    /**
     * Draws the confetti piece on the screen.
     * @param g2d Graphics2D object from paintComponent
     */
    public void draw(Graphics2D g2d) {
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle), x, y);
        Shape rotatedConfetti = at.createTransformedShape(this);
        g2d.setColor(color);
        g2d.draw(rotatedConfetti);
        g2d.fill(rotatedConfetti);
    }

}
