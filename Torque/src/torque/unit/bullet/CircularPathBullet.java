package torque.unit.bullet;

public class CircularPathBullet extends Bullet {
	protected float realX;
	protected float realY;
	protected float phi;
	protected float r2 = 20.0f;

	public CircularPathBullet(float x, float y, float theta) {
		super(x, y, theta, 3.0f);
		realX = x + r2 * (float) Math.cos(theta);
		realY = y + r2 * (float) Math.sin(theta);
		phi = theta + (float) Math.PI / 2.0f;
		speed = 8.0f;
	}

	@Override
	public void move() {
		realX += speed * (float) Math.cos(theta);
		realY += speed * (float) Math.sin(theta);
		phi += (float) Math.PI / 5.0f;
		if(phi > (float) Math.PI * 2.0f)
			phi -= (float) Math.PI * 2.0f;
		x = realX + r2 * (float) Math.cos(phi);
		y = realY + r2 * (float) Math.sin(phi);
	}
}
