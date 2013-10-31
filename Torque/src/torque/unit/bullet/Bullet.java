package torque.unit.bullet;

import java.awt.*;
import java.awt.geom.*;

import torque.unit.*;
import torque.unit.abilities.*;
import torque.unit.enemy.*;

public class Bullet extends Unit implements Moveable, Drawable {
	protected float speed;
	protected float theta;
	protected int damage;

	protected Color color = null;

	public Bullet(float x, float y, float theta) {
		this(x, y, theta, 3.0f);
	}

	protected Bullet(float x, float y, float theta, float r) {
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.r = r;
		speed = 8.0f;
		damage = 1;

		shape = new Ellipse2D.Float(x - r, y - r, 2.0f * r, 2.0f * r);

		color = Color.CYAN;
	}

	public int getDamage() {
		return damage;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		Ellipse2D e = (Ellipse2D.Float) shape;
		e.setFrame(x - r, y - r, 2.0f * r, 2.0f * r);
		g.fill(e);
	}

	@Override
	public void move() {
		x += speed * (float) Math.cos(theta);
		y += speed * (float) Math.sin(theta);
	}

	/**
	 * 
	 * @param e
	 * @return true if this bullet has to disappear
	 */
	public boolean attack(Enemy e) {
		if(e != null)
			e.damage(damage);
		return true;
	}

	public boolean collisionCheck(Enemy e) {
		if(e instanceof BlueEnemy) {
			if(x > e.getX() - e.getR() && x < e.getX() + e.getR() && y > e.getY() - e.getR() && y < e.getY() + e.getR())
				return true;
			return false;
		}
		float dx = x - e.getX();
		float dy = y - e.getY();
		float distance = dx * dx + dy * dy;
		if(distance < (r + e.getR()) * (r + e.getR())) {
			return true;
		}
		return false;
	}

	public void reflect2D(float[] normal) {
		if(normal.length != 2)
			throw new IllegalArgumentException();
		float normalAngle = (float) Math.atan2(normal[1], normal[0]);
		theta += 2.0f * (float) Math.PI - normalAngle;
	}
}
