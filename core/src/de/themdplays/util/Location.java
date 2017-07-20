package de.themdplays.util;

/**
 * Stores two floats to define a location
 */
public class Location {

	private float x;
	private float y;
	
	public Location(float x, float y) {
		this.x = x;
		this.y = y;
	}

    /**
     * @return x
     */
	public float getX() {
		return x;
	}

    /**
     * Sets x
     * @param x
     */
	public void setX(float x) {
		this.x = x;
	}

    /**
     * @return y
     */
	public float getY() {
		return y;
	}

    /**
     * Sets y
     * @param y
     */
	public void setY(float y) {
		this.y = y;
	}
	
}
