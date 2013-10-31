package torque.unit.enemy;

import java.awt.*;
import java.awt.geom.*;

import torque.ui.panel.*;
import torque.ui.panel.playpanel.*;
import torque.unit.bullet.*;

public class TwoBulletBasicEnemy extends BasicAttackEnemy {
	public static final Color color = new Color(0x943E3F);

	public TwoBulletBasicEnemy(PlayPanel pPanel) {
		super(pPanel);
		
		name = "Two Bullet Basic Enemy";

		health = 32.0f;
		speed = 0.8f;
		score = 182;
		exp = 10.3f;
		phi = (float) Math.random() * (float) Math.PI * 2.0f;

		r = 13.0f;
		x = (float) Math.random() * ((float) GamePanel.PPANEL_WIDTH - r);
		y = (float) Math.random() * ((float) GamePanel.PPANEL_HEIGHT - r);

		shape = new GeneralPath();

		xs = new float[5];
		ys = new float[5];
	}

	@Override
	public void attack() {
		float attackAngle = theta + (float) Math.PI / 8.0f;
		Bullet b = new SlowSmallBullet(x + r * (float) Math.cos(attackAngle), y + r * (float) Math.sin(attackAngle), attackAngle);
		pPanel.getEnemybullets().add(b);

		attackAngle = theta - (float) Math.PI / 8.0f;
		b = new SlowSmallBullet(x + r * (float) Math.cos(attackAngle), y + r * (float) Math.sin(attackAngle), attackAngle);
		pPanel.getEnemybullets().add(b);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);

		for(int i = 0; i < 5; i++) {
			xs[i] = x + 1.2f * r * (float) Math.sin(phi + (float) i * (float) Math.PI / 2.5f);
			ys[i] = y + 1.2f * r * (float) Math.cos(phi + (float) i * (float) Math.PI / 2.5f);
		}

		Path2D p = (GeneralPath) shape;
		p.reset();
		p.moveTo(xs[0], ys[0]);
		for(int i = 0; i < 5; i++)
			p.lineTo(xs[i], ys[i]);
		p.closePath();

		g.fill(p);

		// g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 *
		// r)); // 실제 타격범위 표시
	}
}
