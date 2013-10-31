package torque.unit.bullet;

import java.awt.*;

import torque.unit.enemy.*;

public class FireBallBullet extends BigBullet {

	public FireBallBullet(float x, float y, float theta) {
		super(x, y, theta, 10.0f);

		damage = 2;
		speed = 4.5f;

		color = Color.RED;
	}

	@Override
	public boolean attack(Enemy e) {
		super.attack(e);
		return false;
	}
}
