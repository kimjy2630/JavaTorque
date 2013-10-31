package torque.unit.bullet;

import java.awt.*;
import java.awt.geom.*;

import torque.ui.frame.*;
import torque.ui.panel.playpanel.*;
import torque.unit.enemy.*;

public class GuidedBullet extends Bullet {
	private int objectNumber;
	private boolean isUsed = false;
	private Enemy object = null;
	private PlayPanel_Original pPanel = null;

	public GuidedBullet(float x, float y, float theta, int objectNumber, PlayPanel_Original pPanel) {
		super(x, y, theta, 3.0f);
		damage = 30;
		this.objectNumber = objectNumber;
		this.pPanel = pPanel;
		speed = 9;

		new Mover().start();
	}

	@Override
	public boolean attack(Enemy e) {
		if(e != null)
			e.damage(damage);
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.LIGHT_GRAY);
		Ellipse2D e = (Ellipse2D.Float) shape;
		e.setFrame(x - r, y - r, 2.0f * r, 2.0f * r);
		g.fill(e);
	}

	@Override
	public void move() {
	}

	public void move2() {
		if(object != null) {
			theta = (float) Math.atan2(object.getY() - y, object.getX() - x);

			x += speed * Math.cos(theta);
			y += speed * Math.sin(theta);
		}
	}

	class Mover extends Thread {
		@Override
		public void run() {
			try {
				while(!isUsed) {
					if(object == null || !pPanel.getEnemies().contains(object)) {
						if(pPanel.getEnemies().size() > objectNumber) {
							object = pPanel.getEnemies().get(objectNumber);
						}
					}
					theta = (float) Math.atan2(object.getY() - y, object.getX() - x);

					move2();

					Thread.sleep(MainFrame.frameInterval);

				}
			} catch(Exception e) {
			}
		}
	}
}
