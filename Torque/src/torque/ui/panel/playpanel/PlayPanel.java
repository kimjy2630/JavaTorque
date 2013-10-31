package torque.ui.panel.playpanel;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import javax.swing.*;

import torque.ui.frame.*;
import torque.ui.panel.*;
import torque.unit.abilities.*;
import torque.unit.bullet.*;
import torque.unit.enemy.*;
import torque.unit.player.*;

public abstract class PlayPanel extends JPanel {

	/**
	 * An object of player.
	 */
	protected Player player = null;

	/**
	 * List of torque.unit.enemy that is in the field now.
	 */
	protected LinkedList<Enemy> enemies = null;
	/**
	 * List of torque.unit.enemy that will appear soon.
	 */
	protected LinkedList<EnemyGeneratingCircle> readyEnemies = null;
	protected LinkedList<EnemyDisappearingCircle> diedEnemies = null;
	protected LinkedList<Bullet> enemyBullets = null;
	protected LinkedList<Player> playerList = new LinkedList<>();
	/**
	 * List of threads that are used in the game.
	 */
	protected LinkedList<Thread> threads = new LinkedList<>();
	protected LinkedList<LinkedList<? extends Drawable>> drawings = new LinkedList<>();

	/**
	 * The score that you have gotten. Initial value is 0.
	 */
	protected int score = 0;

	protected int levelNum = 60;
	protected int levelNum2 = 10;

	/**
	 * Whether the game has paused. Default value is false.
	 */
	protected boolean paused = false;
	protected boolean gameOver = false;

	protected GamePanel gPanel = null;

	public PlayPanel(GamePanel gPanel) {
		this.gPanel = gPanel;

		// setBackground(Color.MAGENTA);
		// setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - PPANEL_WIDTH / 2, 50, GamePanel.PPANEL_WIDTH, GamePanel.PPANEL_HEIGHT);
		setBounds(0, 50, GamePanel.PPANEL_WIDTH, GamePanel.PPANEL_HEIGHT);
	}

	public void startGame() {
		drawings.add(diedEnemies);
		drawings.add(readyEnemies);
		drawings.add(enemies);
		drawings.add(enemyBullets);
		for(Player p : playerList)
			drawings.add(p.getBullets());
		drawings.add(playerList);

		threads.add(getEnemyGenerator());
		threads.add(getEnemyProcessor());

		for(Thread th : threads)
			if(!th.isAlive())
				th.start();
	}

	public void gameOver() {
		gameOver = true;
		drawings.clear();
		drawings.add(diedEnemies);
		for(Enemy e : enemies)
			addDiedEnemy(e);

		enemies.clear();
		readyEnemies.clear();
		enemyBullets.clear();
		drawings.remove(playerList);

		player.getBullets().clear();

		threads.clear();

		Thread t = new Thread() {
			@Override
			public void run() {
				while(!isInterrupted())
					try {
						Thread.sleep(MainFrame.frameInterval);
					} catch(InterruptedException e) {
					}
				diedEnemies.clear();
				for(Thread th : threads)
					th.interrupt();
				drawings.clear();
				gameOver = false;
			}
		};
		t.start();

		MainFrame.mainFrame.getRPanel().gameOver(score, t);
	}

	protected Thread getEnemyGenerator() {
		return new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(60 * MainFrame.frameInterval);

					while(!Thread.currentThread().isInterrupted() && !gameOver) {
						if(!paused)
							createEnemy();

						Thread.sleep(MainFrame.frameInterval);
					}
				} catch(InterruptedException e) {
				}
			}
		};
	}

	protected Thread getEnemyProcessor() {
		return new Thread() {
			@Override
			public void run() {
				try {
					while(!Thread.currentThread().isInterrupted()) {
						enemyProcess();
						Thread.sleep(MainFrame.frameInterval);
					}
				} catch(InterruptedException e) {
				}
			}
		};
	}

	static int n = 0;

	protected void enemyProcess() {
		requestFocus();

		if(!gameOver)
			if(!paused) {
				System.out.println(n++);

				if(player != null && player.getBullets() != null && enemies != null && enemyBullets != null) {

					player.move();
					player.attack();

					for(Enemy e : (LinkedList<Enemy>) (enemies.clone()))
						if(e != null)
							e.move();

					for(Bullet b : (LinkedList<Bullet>) (player.getBullets().clone()))
						if(b != null) {
							b.move();
							if(b.getX() < 0 || b.getX() > GamePanel.PPANEL_WIDTH || b.getY() < 0 || b.getY() > GamePanel.PPANEL_HEIGHT)
								player.getBullets().remove(b);

							for(Enemy e : (LinkedList<Enemy>) (enemies.clone()))
								if(e != null) {
									if(b.collisionCheck(e))
										if(e.hit(b))
											player.getBullets().remove(b);

									if(player.collisionCheck(e)) {
										player.die();
										gameOver();
										return;
									}
								}
						}

					for(Bullet b : (LinkedList<Bullet>) (enemyBullets.clone()))
						if(b != null) {
							b.move();
							if(b.getX() < 0 || b.getX() > GamePanel.PPANEL_WIDTH || b.getY() < 0 || b.getY() > GamePanel.PPANEL_HEIGHT)
								enemyBullets.remove(b);
							if(player.collisionCheck(b)) {
								player.die();
								gameOver();
								return;
							}
						}
				}
				repaint();
			} else
				;
		else
			repaint();
	}

	protected class EnemyDisappearingCircle implements Drawable {
		float x;
		float y;
		float r = 0.0f;

		Color color = null;

		int called = 0;

		private EnemyDisappearingCircle(Enemy enemy) {
			x = enemy.getX();
			y = enemy.getY();
		}

		@Override
		public void draw(Graphics2D g) {
			if(called < 25) {
				color = Color.RED;
				r += 2.0f;
			} else if(called < 50)
				r -= 2.0f;
			else if(called < 75) {
				color = Color.BLUE;
				r += 2.0f;
			} else if(called < 100)
				r -= 2.0f;
			else if(called < 125) {
				color = Color.GREEN;
				r += 2.0f;
			} else if(called < 150)
				r -= 2.0f;
			else {
				diedEnemies.remove(this);
				return;
			}

			Stroke s = g.getStroke();
			g.setStroke(new BasicStroke(4.0f));
			g.setColor(color);
			Ellipse2D e = new Ellipse2D.Float(x - r, y - r, r * 2.0f, r * 2.0f);
			g.draw(e);
			called++;
			g.setStroke(s);
		}
	}

	/**
	 * class that make enemy in ready to appear.
	 * 
	 * @author ÀçÀ±
	 * @see EnemyGenerator
	 */
	protected class EnemyGeneratingCircle implements Drawable {
		protected Enemy e;
		protected float x;
		protected float y;
		protected float r;

		public EnemyGeneratingCircle(Enemy e) {
			this.e = e;
			x = e.getX();
			y = e.getY();
			r = 80.0f;
			readyEnemies.add(this);
		}

		@Override
		public void draw(Graphics2D g) {
			g.setColor(Color.WHITE);
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

		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append(e).append(" at (").append((int) x).append(", ").append((int) y).append(")");
			return s.toString();
		}
	}

	// //////////////////////////////////////////////////
	// //////////////////MODIFIER/////////////////////
	// //////////////////////////////////////////////////
	public void addDiedEnemy(Enemy enemy) {
		if(diedEnemies != null)
			diedEnemies.add(new EnemyDisappearingCircle(enemy));
	}

	/**
	 * Adds score.
	 * 
	 * @param score
	 *            the amount of score to add
	 */
	public void addScore(int score) {
		this.score += score;
		gPanel.setScore(this.score);
	}

	/**
	 * Switches the state which indicates whether the game is paused.
	 */
	public void pause() {
		paused = !paused;
	}

	// //////////////////////////////////////////////////
	// ///////////////////GETTER///////////////////////
	// //////////////////////////////////////////////////
	public LinkedList<Bullet> getEnemybullets() {
		return enemyBullets;
	}

	public LinkedList<Enemy> getEnemies() {
		return enemies;
	}

	public int getScore() {
		return score;
	}

	/**
	 * Indicates whether the game is paused.
	 * 
	 * @return true if game is paused; else return false
	 */
	public boolean isPaused() {
		return paused;
	}

	public LinkedList<Thread> getThreads() {
		return threads;
	}

	public Player getPlayer() {
		return player;
	}

	// //////////////////////////////////////////////////
	// /////////////UNIMPLEMENTED//////////////////
	// //////////////////////////////////////////////////
	protected abstract void createEnemy();
}
