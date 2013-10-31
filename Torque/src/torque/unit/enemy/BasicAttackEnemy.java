package torque.unit.enemy;

import java.awt.*;
import java.awt.geom.*;

import torque.ui.frame.*;
import torque.ui.panel.*;
import torque.ui.panel.playpanel.*;
import torque.unit.bullet.*;

public class BasicAttackEnemy extends AttackableEnemy {
	public static final Color color = new Color(0x055000);

	protected float phi;// 회전 각도

	float[] xs = null;
	float[] ys = null;

	private Attack attackThread = null;

	public BasicAttackEnemy(PlayPanel pPanel) {
		super(pPanel);

		name = "Basic Attack Enemy";

		health = 20.0f;
		speed = 1.0f;
		score = 140;
		exp = 8.2f;
		phi = (float) Math.random() * (float) Math.PI * 2.0f;

		r = 14.0f;
		x = (float) Math.random() * ((float) GamePanel.PPANEL_WIDTH - r);
		y = (float) Math.random() * ((float) GamePanel.PPANEL_HEIGHT - r);

		shape = new GeneralPath();

		xs = new float[6];
		ys = new float[6];
	}

	@Override
	public void move() {
		super.move();

		phi += (float) Math.PI / 75.0f;
		if(phi > (float) Math.PI * 2.0f)
			phi -= (float) Math.PI * 2.0f;
	}

	@Override
	public void attack() {
		BigBullet b = new BigBullet(x + r * (float) Math.cos(theta), y + r * (float) Math.sin(theta), theta);
		pPanel.getEnemybullets().add(b);
	}

	@Override
	protected void die() {
		if(!attackThread.isInterrupted())
			attackThread.interrupt();
		super.die();
	}

	@Override
	public void setup() {
		super.setup();
		attackThread = new Attack();
		attackThread.start();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);

		for(int i = 0; i < 6; i++) {
			xs[i] = x + 1.2f * r * (float) Math.sin(phi + (float) i * (float) Math.PI / 3.0f);
			ys[i] = y + 1.2f * r * (float) Math.cos(phi + (float) i * (float) Math.PI / 3.0f);
		}

		Path2D p = (GeneralPath) shape;
		p.reset();
		p.moveTo(xs[0], ys[0]);
		for(int i = 0; i < 6; i++)
			p.lineTo(xs[i], ys[i]);
		p.closePath();

		g.fill(p);

		// g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 *
		// r)); // 실제 타격범위 표시
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
					Thread.sleep(MainFrame.frameInterval * 30);
				}
			} catch(InterruptedException e) {
			} finally {
				System.out.println("Attack end");
			}
		}
	}
}
