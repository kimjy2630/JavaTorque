package torque.unit.enemy;

import java.awt.*;
import java.awt.geom.*;

import torque.ui.frame.*;
import torque.ui.panel.*;
import torque.ui.panel.playpanel.*;
import torque.unit.bullet.*;

public class GreenEnemy extends Enemy {

	public static final Color color = Color.GREEN;

	private Cure cure;

	public GreenEnemy(PlayPanel pPanel) {
		super(pPanel);

		name = "Green Enemy";

		MAX_HEALTH = 15.0f;
		health = MAX_HEALTH;
		// speed = 1;
		speed = 1.5f;
		score = 112;
		exp = 1.8f;

		r = 7.0f;
		x = (float) Math.random() * ((float) GamePanel.PPANEL_WIDTH - r);
		y = (float) Math.random() * ((float) GamePanel.PPANEL_HEIGHT - r);

		shape = new Ellipse2D.Float(x - r, y - r, 2.0f * r, 2.0f * r);

		cure = new Cure();
		cure.start();
	}

	@Override
	public boolean hit(Bullet b) {
		boolean ret = super.hit(b);

		x -= speed * (float) Math.cos(theta) * 5.0f;
		y -= speed * (float) Math.sin(theta) * 5.0f;

		return ret;
	}

	private void cure() {
		if(health < MAX_HEALTH) {
			health += 1;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		Ellipse2D e = (Ellipse2D.Float) shape;
		e.setFrame(x - r, y - r, 2.0f * r, 2.0f * r);
		g.draw(e);
		Line2D l = new Line2D.Float(x - r, y, x - r + 2.0f * r * health / MAX_HEALTH, y);
		g.draw(l);
	}

	public class Cure extends Thread {
		@Override
		public void run() {
			while(true) {
				cure();
				try {
					Thread.sleep(MainFrame.frameInterval * 6);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
