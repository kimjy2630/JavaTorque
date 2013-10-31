package torque.unit.bullet;

import java.awt.*;

public class BigBullet extends Bullet {

	public BigBullet(float x, float y, float theta) {
		this(x, y, theta, 8.0f);
	}

	public BigBullet(float x, float y, float theta, float r) {
		super(x, y, theta, r);
		damage = 12;
		speed = 1.5f;

		color = Color.PINK.darker();
	}

}
