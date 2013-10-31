package torque.ui.panel.playpanel;

import java.awt.*;
import java.awt.geom.*;

import torque.ui.frame.*;
import torque.ui.panel.*;
import torque.unit.enemy.*;
import torque.unit.enemy.boss.*;

public class PlayPanel_Extension3 extends PlayPanel_Extension2 {

	public PlayPanel_Extension3(GamePanel gPanel) {
		super(gPanel);
	}

	@Override
	public void startGame() {
		threads.add(getBossGenerator());
		super.startGame();
	}

	protected Thread getBossGenerator() {
		return new Thread() {
			@Override
			public void run() {
				try {
					// Thread.sleep(100000 * MainFrame.frameInterval);
					Thread.sleep(100 * MainFrame.frameInterval);

					while(!Thread.currentThread().isInterrupted()) {
						if(!paused)
							createBoss();

						Thread.sleep(1000 * MainFrame.frameInterval);
					}
				} catch(InterruptedException e) {
				}
			}
		};
	}

	protected void createBoss() {
		new BossGeneratingCircle(new NormalBoss(PlayPanel_Extension3.this));
		// TODO BossGeneratingCircle¸¸µé±â
	}

	protected class BossGeneratingCircle extends EnemyGeneratingCircle {

		public BossGeneratingCircle(Enemy e) {
			super(e);
			r = 160.0f;
		}

		@Override
		public void draw(Graphics2D g) {
			g.setColor(Color.RED);
			Ellipse2D circle = new Ellipse2D.Float(x - r, y - r, 2.0f * r, 2.0f * r);
			g.draw(circle);
			r -= 1.0f;
			if(r < 0) {
				readyEnemies.remove(this);
				enemies.add(e);
				e.move();
				e.setup();
			}
		}
	}
}
