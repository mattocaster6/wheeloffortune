package game;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the Wheel of Fortune's main logic
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class Wheel {

    //Declares class fields
    private final double WHEEL_FRICTION = 0.08;
    private final ArrayList<WheelSegment> segments;
    private double currentAngle;
    private double spinVelocity;
    private final Random rand = new Random();

    /**
     * Initializes and instantiates wheel objects and values.
     */
    public Wheel() {
        this.segments = new ArrayList<>();
        initializeSegments();
        currentAngle = 0;
        spinVelocity = 0;
    }

    /**
     * Adds segments to Wheel of Fortune
     */
    private void initializeSegments() {
        segments.add(new WheelSegment(SegmentType.MONEY, 400));
        segments.add(new WheelSegment(SegmentType.LOSE_A_TURN, 0));
        segments.add(new WheelSegment(SegmentType.MONEY, 200));
        segments.add(new WheelSegment(SegmentType.MONEY, 300));
        segments.add(new WheelSegment(SegmentType.MONEY, 500));
        segments.add(new WheelSegment(SegmentType.MONEY, 250));
        segments.add(new WheelSegment(SegmentType.MONEY, 800));
        segments.add(new WheelSegment(SegmentType.MONEY, 1000));
        segments.add(new WheelSegment(SegmentType.BANKRUPT, 0));
        segments.add(new WheelSegment(SegmentType.MONEY, 400));
        segments.add(new WheelSegment(SegmentType.MONEY, 600));
        segments.add(new WheelSegment(SegmentType.MONEY, 150));
        segments.add(new WheelSegment(SegmentType.MONEY, 400));
        segments.add(new WheelSegment(SegmentType.MONEY, 750));
        segments.add(new WheelSegment(SegmentType.FREE_SPIN, 0));
        segments.add(new WheelSegment(SegmentType.MONEY, 400));
        segments.add(new WheelSegment(SegmentType.MONEY, 1000));
        segments.add(new WheelSegment(SegmentType.MONEY, 450));
        segments.add(new WheelSegment(SegmentType.MONEY, 700));
        segments.add(new WheelSegment(SegmentType.BANKRUPT, 0));
        segments.add(new WheelSegment(SegmentType.MONEY, 200));
        segments.add(new WheelSegment(SegmentType.MONEY, 500));
        segments.add(new WheelSegment(SegmentType.MONEY, 900));
        segments.add(new WheelSegment(SegmentType.MONEY, 1000));
    }

    /**
     * Gets the currently selected segment of the Wheel Of Fortune by angle
     *
     * @return returns the currently selected segment of the Wheel of Fortune by angle
     */
    public WheelSegment getSelectedSegment() {
        return segments.get((int) (currentAngle / 15));
    }

    /**
     * Sets starting velocity of wheel to a random value.
     */
    public void startSpin() {
        spinVelocity = 8 + (rand.nextDouble() * 15);
    }

    /**
     * Rotates the wheel according to spin velocity
     */
    public void spin() {
        if (currentAngle + spinVelocity >= 360) {
            currentAngle = (currentAngle + spinVelocity) - 360;
        } else {
            currentAngle += spinVelocity;
        }
        spinVelocity -= WHEEL_FRICTION;
    }

    //------------------ GETTERS AND SETTERS ---------------------//

    /**
     * Gets the current angle of the wheel
     *
     * @return returns the current angle of the wheel
     */
    public double getCurrentAngle() {
        return currentAngle;
    }

    /**
     * Gets the current velocity of the wheel
     *
     * @return returns the current velocity of the wheel.
     */
    public double getSpinVelocity() {
        return spinVelocity;
    }

    /**
     * Sets the angle of wheel, for testing purposes
     * @param angle the angle to set wheel to
     */
    public void setCurrentAngle(double angle) {
        this.currentAngle = angle;
    }

    /**
     * Retrieves a wheel segment from segments list, for testing purposes
     * @param index index of wheel segment in list
     * @return returns the wheel segment at index in segments list
     */
    public WheelSegment getSegmentFromList(int index) {
        return segments.get(index);
    }

    /**
     * Gets segment list, for testing purposes
     * @return returns segment list
     */
    public ArrayList<WheelSegment> getSegments() {
        return segments;
    }
}
