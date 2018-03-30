package com.anecon.taf.client.seetest;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * How to search for an element with swipes
 *
 * @author Michael Hombauer
 */
public class SeeTestSearchStrategy {

    /**
     * In which direction to swipe
     *
     * @see SwipeDirection
     * @see <a href="https://docs.experitest.com/display/public/SA/Direction">SeeTest Documentation - Direction</a>
     */
    private String swipeDirection;

    /**
     * The offset from the end of the screen
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/Offset">SeeTest Documentation - Offset</a>
     */
    private int offset;

    /**
     * How long the swipe should be in milliseconds (the longer, the faster)
     */
    private int swipeTime;

    /**
     * How many swipes to perform before giving up
     */
    private int rounds;

    /**
     * How long to wait between swipes
     */
    private int delay;

    /**
     * Use this constructor for swipeWhileNotFound and elementSwipeWhileNotFound
     */
    public SeeTestSearchStrategy(String swipeDirection, int offset, int swipeTime, int rounds, int delay) {
        this.swipeDirection = swipeDirection;
        this.offset = offset;
        this.swipeTime = swipeTime;
        this.rounds = rounds;
        this.delay = delay;
    }

    /**
     * Use this constructor for swipe
     */
    public SeeTestSearchStrategy(String swipeDirection, int offset, int swipeTime) {
        this.swipeDirection = swipeDirection;
        this.offset = offset;
        this.swipeTime = swipeTime;
    }

    public String getSwipeDirection() {
        return swipeDirection;
    }

    public void setSwipeDirection(String swipeDirection) {
        this.swipeDirection = swipeDirection;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSwipeTime() {
        return swipeTime;
    }

    public void setSwipeTime(int swipeTime) {
        this.swipeTime = swipeTime;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
