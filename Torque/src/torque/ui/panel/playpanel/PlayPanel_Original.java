package torque.ui.panel.playpanel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import torque.ui.panel.*;
import torque.unit.abilities.*;
import torque.unit.enemy.*;
import torque.unit.player.*;
import torque.unit.skills.*;

/**
 * A panel where the game is played.
 * 
 * @author ¿Á¿±
 */
public class PlayPanel_Original extends PlayPanel {
	protected int stage = 1;

	public PlayPanel_Original(GamePanel gPanel) {
		super(gPanel);

		player = new Player(this);
		playerList.add(player);

		enemies = new LinkedList<>();
		readyEnemies = new LinkedList<>();
		diedEnemies = new LinkedList<>();
		enemyBullets = new LinkedList<>();

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_P) {
					pause();
					return;
				}
				if(!paused) {
					if(player != null) {
						switch(key) {
						case KeyEvent.VK_SPACE:
							player.setFocusAttack();
							return;
						case KeyEvent.VK_Z:
							player.useSkill(SkillKeys.Z);
							return;
						case KeyEvent.VK_X:
							player.useSkill(SkillKeys.X);
							return;
						case KeyEvent.VK_C:
							player.useSkill(SkillKeys.C);
							return;
						case KeyEvent.VK_V:
							player.useSkill(SkillKeys.V);
							return;
						case KeyEvent.VK_A:
							player.useSkill(SkillKeys.A);
							return;
						default:
							player.setDirection(key);
						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if(player != null) {
					if(key == KeyEvent.VK_SPACE) {
						player.removeFocusAttack();
						return;
					}
					player.removeDirection(key);
				}
			}
		});

	}

	@Override
	public void paint(Graphics g) {
		Image image = createImage(GamePanel.PPANEL_WIDTH, GamePanel.PPANEL_HEIGHT);
		Graphics2D g1 = null;
		if(image != null) {
			g1 = (Graphics2D) image.getGraphics();
			g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g1.drawImage(gPanel.getBGImg().get(stage - 1), 0, 0, this);

			for(LinkedList<? extends Drawable> l : (LinkedList<LinkedList<? extends Drawable>>) (drawings.clone()))
				if(l != null)
					for(Drawable d : (LinkedList<Drawable>) (l.clone()))
						if(d != null)
							d.draw(g1);
						else
							System.out.println("d is null");
				else if(l == readyEnemies)
					System.out.println(readyEnemies);
				else
					System.out.println("l is null");

			g1.setColor(Color.WHITE);
			if(player != null) {
				g1.drawString("(" + (int) player.getX() + ", " + (int) player.getY() + ", " + (int) (player.getTheta() * 180.0 / Math.PI) + ")", 0, 10);
				g1.drawString("bullets : " + player.getBullets().size(), 0, 20);
			}
			if(enemies != null)
				g1.drawString("enemy : " + enemies.size(), 0, 30);

			if(player != null)
				gPanel.getSPanel().refreshText(player);

			g.drawImage(image, 0, 0, this);

		}
	}

	@Override
	protected void createEnemy() {
		int c;

		c = (int) (new Random().nextInt(levelNum) * 1.5f);
		if(c == 0)
			new EnemyGeneratingCircle(new RedEnemy(PlayPanel_Original.this));

		c = new Random().nextInt((int) (levelNum * levelNum2 * 0.8f));
		if(c == 0)
			new EnemyGeneratingCircle(new BlueEnemy(PlayPanel_Original.this));

		c = new Random().nextInt(levelNum * (int) (levelNum2 * 11.0f / 9.0f));
		if(c == 0)
			new EnemyGeneratingCircle(new GreenEnemy(PlayPanel_Original.this));

		c = new Random().nextInt(levelNum * levelNum2 * 2);
		if(c == 0)
			new EnemyGeneratingCircle(new BrownEnemy(PlayPanel_Original.this));

		levelNum = Math.max(10, (int) Math.ceil(60.0f - (float) score * 0.002f));
		levelNum2 = Math.max(3, (int) Math.ceil(10.0f - (float) score * 0.0005f));
	}

}