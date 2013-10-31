package torque.unit.enemy;

import java.awt.*;
import java.awt.geom.*;

import torque.ui.panel.*;
import torque.ui.panel.playpanel.*;
import torque.unit.bullet.*;

public class BlueEnemy extends Enemy {

	public static final Color color = Color.BLUE;

	public BlueEnemy(PlayPanel pPanel) {
		super(pPanel);

		name = "Blue Enemy";

		// speed = 1;
		speed = 1.5f;
		score = 88;
		exp = 2.7f;

		r = 7.0f;
		x = (float) Math.random() * ((float) GamePanel.PPANEL_WIDTH - r);
		y = (float) Math.random() * ((float) GamePanel.PPANEL_HEIGHT - r);

		shape = new Rectangle2D.Float(x - r, y - r, r * 2.0f, r * 2.0f);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		Rectangle2D rect = (Rectangle2D.Float) shape;
		rect.setRect(x - r, y - r, 2.0f * r, 2.0f * r);
		g.draw(rect);
	}

	@Override
	public boolean hit(Bullet b) {
		x -= speed * (float) Math.cos(theta) * 20.0f;
		y -= speed * (float) Math.sin(theta) * 20.0f;
		return b.attack(null);
	}

	@Override
	public void die() {
		pPanel.getEnemies().remove(this);
		pPanel.addScore(score);
		player.addMP(1);
		player.addExp((int) (exp));
	}
}
