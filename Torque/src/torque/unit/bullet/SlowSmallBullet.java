package torque.unit.bullet;

import java.awt.*;
import java.awt.geom.*;

public class SlowSmallBullet extends Bullet {

	public SlowSmallBullet(float x, float y, float theta) {
		this(x, y, theta, 3.0f);

	}

	protected SlowSmallBullet(float x, float y, float theta, float r) {
		super(x, y, theta, r);
		speed = 2.5f;
		damage = 1;

		shape = new Ellipse2D.Float(x - r, y - r, 2.0f * r, 2.0f * r);

		color = Color.CYAN;
	}
}
