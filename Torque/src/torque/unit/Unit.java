package torque.unit;

import java.awt.*;

/**
 * Representative class of all units in the game.
 * 
 * @author ¿Á¿±
 * 
 */
public abstract class Unit {
	/**
	 * The shape of this unit
	 */
	protected Shape shape = null;
	/**
	 * The x-coordinate of this unit
	 */
	protected float x;
	/**
	 * The y-coordinate of this unit
	 */
	protected float y;
	/**
	 * The size of this unit
	 */
	protected float r;

	/**
	 * Gets the shape of this unit.
	 * 
	 * @return shape of this unit
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * Gets the x-coordinate of this unit.
	 * 
	 * @return x-coordinate of this unit
	 */
	public float getX() {
		return x;
	}

	/**
	 * Gets the y-coordinate of this unit.
	 * 
	 * @return y-coordinate of this unit
	 */
	public float getY() {
		return y;
	}

	/**
	 * Gets the size of this unit,
	 * 
	 * @return size of this unit
	 */
	public float getR() {
		return r;
	}
}
