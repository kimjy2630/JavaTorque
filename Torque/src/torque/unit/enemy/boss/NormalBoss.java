package torque.unit.enemy.boss;

import java.awt.*;
import java.awt.geom.*;

import torque.ui.frame.*;
import torque.ui.panel.*;
import torque.ui.panel.playpanel.*;
import torque.unit.bullet.*;
import torque.unit.enemy.*;

public class NormalBoss extends AttackableEnemy {
	public static Color color = Color.PINK;

	private Attack attackThread = null;

	public NormalBoss(PlayPanel pPanel) {
		super(pPanel);

		name = "Normal Boss";

		// speed = 1;
		health = 500f;
		speed = 0.5f;
		score = 880;
		exp = 58f;

		r = 70.0f;
		x = (float) Math.random() * ((float) GamePanel.PPANEL_WIDTH - r);
		y = (float) Math.random() * ((float) GamePanel.PPANEL_HEIGHT - r);

		shape = new Ellipse2D.Float(x - r, y - r, r * 2.0f, r * 2.0f);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		Ellipse2D oval = (Ellipse2D.Float) shape;
		oval.setFrame(x - r, y - r, 2.0f * r, 2.0f * r);
		g.fill(oval);
	}

	@Override
	public void attack() {
		BigBullet b = new BigBullet(x + r * (float) Math.cos(theta), y + r * (float) Math.sin(theta), theta);
		pPanel.getEnemybullets().add(b);
	}

	@Override
	public void setup() {
		super.setup();
		attackThread = new Attack();
		attackThread.start();
	}

	@Override
	protected void die() {
		if(!attackThread.isInterrupted())
			attackThread.interrupt();
		super.die();
	}

	protected class Attack extends Thread {
		public Attack() {
			pPanel.getThreads().add(this);
		}

		@Override
		public void run() {
			try {
				while(!Thread.currentThread().isInterrupted()) {
					if(!pPanel.isPaused()) {
						attack();
					}
					Thread.sleep(MainFrame.frameInterval * 12);
				}
			} catch(InterruptedException e) {
			} finally {
				System.out.println("Attack end");
			}
		}
	}
}
