package game;

/**
 * Enumeration structure for wheel of fortune segment types.
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public enum SegmentType {
    /**
     * Wheel segments result contains a score
     */
    MONEY,
    /**
     * Wheel segment results in player bankruptcy
     */
    BANKRUPT,
    /**
     * Wheel segment results in a free spin
     */
    FREE_SPIN,
    /**
     * Wheel segment results in a lost turn
     */
    LOSE_A_TURN
}
