package torque.unit.enemy;

import torque.ui.panel.*;
import torque.ui.panel.playpanel.*;
import torque.unit.*;
import torque.unit.abilities.*;
import torque.unit.bullet.*;
import torque.unit.player.*;

/**
 * The prototype of all torque.unit.enemy. Can't be instantiated.
 * 
 * @author ¿Á¿±
 * 
 */
public abstract class Enemy extends Unit implements Moveable, Drawable {
	/**
	 * The maximum value of health of this object.
	 */
	public static float MAX_HEALTH;
	/**
	 * Health of torque.unit.enemy.
	 */
	protected float health;
	/**
	 * The speed this enemy moves.
	 */
	protected float speed;
	/**
	 * The angle from this enemy to the torque.unit.player.
	 */
	protected float theta;
	/**
	 * The amount of score that the torque.unit.player gets when this enemy is removed.
	 */
	protected int score;
	/**
	 * The amount of experience point that the torque.unit.player gets when this enemy is removed.
	 */
	protected float exp;

	protected boolean ready = false;

	protected String name = "Enemy";

	/**
	 * The game that this enemy is in.
	 */
	protected PlayPanel pPanel = null;

	protected Player player = null;

	/**
	 * A constructor setting the game that this object is in. Must be called from subclasses of Enemy.
	 * 
	 * @param pPanel
	 *            the game that this enemy is in.
	 */
	protected Enemy(PlayPanel pPanel) {
		this.pPanel = pPanel;
		player = this.pPanel.getPlayer();
	}

	/**
	 * The method called when this enemy is hit by a bullet.
	 * 
	 * @param b
	 *            a bullet hitting this enemy
	 */
	public boolean hit(Bullet b) {
		boolean ret = b.attack(this);
		if(health <= 0)
			die();
		return ret;
	}

	/**
	 * Move this enemy.
	 */
	@Override
	public void move() {

		if(player != null)
			theta = (float) Math.atan2(player.getY() - y, player.getX() - x);

		x += speed * Math.cos(theta);
		y += speed * Math.sin(theta);

		outOfBoundCheck();
	}

	/**
	 * Gives this object damage.
	 * 
	 * @param damage
	 *            amount of damage this object receive
	 */
	public void damage(int damage) {
		health -= damage;
	}

	public void setup() {
		ready = true;
	}

	/**
	 * Checks if this enemy is out of the bound of PlayPanel and then, remove this enemy if this is out of the bound.
	 */
	public void outOfBoundCheck() {
		if(x < 0 || x > GamePanel.PPANEL_WIDTH || y < 0 || y > GamePanel.PPANEL_HEIGHT) {
			die();
		}
	}

	/**
	 * Removes this enemy and add score and experience point to torque.unit.player.
	 */
	protected void die() {
		pPanel.getEnemies().remove(this);
		pPanel.addScore(score);
		player.addMP(1);
		player.addExp((int) (exp));
		pPanel.addDiedEnemy(this);
	}

	/**
	 * Gets the health of this enemy
	 * 
	 * @return health of this enemy
	 */
	public float getHealth() {
		return health;
	}

	/**
	 * Gets the amount of score which torque.unit.player obtains when this enemy dies.
	 * 
	 * @return amount of score which this object gives
	 */
	public int getScore() {
		return score;
	}

	@Override
	public String toString() {
		return name + " at (" + x + ", " + y + ")";
	}
}
