package game;

/**
 * This class represents a wheel segment for a Wheel of Fortune
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class WheelSegment {

    //Declares class fields
    private final SegmentType type;
    private final int value;

    /**
     * Initializes wheel segment values
     * @param type type of segment
     * @param value money value of segment
     */
    public WheelSegment(SegmentType type, int value) {
        this.type = type;
        this.value = value;
    }

    // ------------- GETTERS AND SETTERS ----------------//
    /**
     * Gets the type of the wheel segment
     *
     * @return returns the type of the wheel segment
     */
    public SegmentType getType() {
        return type;
    }

    /**
     * Gets the segment type formatted as a normal string
     *
     * @return returns segment type formatted as a normal string
     */
    public String getTypeAsString() {
        switch (type) {
            case MONEY:
                return "Money";
            case BANKRUPT:
                return "Bankrupt";
            case FREE_SPIN:
                return "Free Spin";
            case LOSE_A_TURN:
                return "Lose a Turn";
        }

        return "Unknown";
    }

    /**
     * Gets the score value of the wheel segment
     *
     * @return returns the score value of the wheel segment
     */
    public int getValue() {
        return value;
    }

}
