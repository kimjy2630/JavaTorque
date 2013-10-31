package torque.unit.player;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

import torque.ui.frame.*;
import torque.ui.panel.*;
import torque.ui.panel.playpanel.*;
import torque.unit.*;
import torque.unit.abilities.*;
import torque.unit.bullet.*;
import torque.unit.skills.*;

public class Player extends Unit implements Attackable, Moveable, Invincible, Drawable {

	private float max_mp = 100.0f;
	private float mp = 0.0f;
	private float mpInc = 0.2f;

	private int level;
	private float exp = 0.0f;
	private float max_exp = 30.0f;

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;

	private boolean focus = false;
	private boolean isInvincible = false;
	private boolean bulletReflecting = false;
	private boolean isAlive;

	private float speed = 3.0f;

	private float theta;

	private HashMap<SkillKeys, Skill> skills = null;

	public Color color = Color.YELLOW;
	public Color color_invincible = Color.WHITE;

	private LinkedList<Bullet> bullets = null;

	private PlayPanel_Original pPanel = null;

	public Player(PlayPanel_Original pPanel) {
		x = (float) GamePanel.PPANEL_WIDTH / 2.0f;
		y = (float) GamePanel.PPANEL_HEIGHT / 2.0f;
		r = 7.5f;
		theta = 0.0f;
		level = 1;
		shape = new Ellipse2D.Float(x - r, y - r, 2.0f * r, 2.0f * r);
		this.pPanel = pPanel;

		bullets = new LinkedList<>();

		skills = new HashMap<>();

		addSkill(SkillKeys.Z, new BulletRadiation(this));
		addSkill(SkillKeys.X, new AutoMissile(this));
		addSkill(SkillKeys.C, new InvincibleSkill(this));
		addSkill(SkillKeys.V, new BulletRefleting(this));
		addSkill(SkillKeys.A, new FireBall(this));

		isAlive = true;

		new MPRecover().start();
	}

	public synchronized LinkedList<Bullet> getBullets() {
		if(bullets == null)
			return null;
		return bullets;
	}

	@Override
	public void attack() {
		Bullet b = new Bullet(x + r * (float) Math.cos(theta), y + r * (float) Math.sin(theta), theta + (float) Math.random() * (float) Math.PI / 15.0f);
		// Bullet b = new GuidedBullet(x + r * (float) Math.cos(theta), y + r * (float) Math.sin(theta), theta + (float) Math.random() * (float) Math.PI / 15.0f, (int) (pPanel.getEnemies().size() * Math.random()), pPanel);
		bullets.add(b);
	}

	@Override
	public void draw(Graphics2D g) {
		if(bulletReflecting) {
			g.setColor(Color.BLUE.darker());
			g.draw(new Ellipse2D.Float(x - r * 1.5f, y - r * 1.5f, 3.0f * r, 3.0f * r));
		}
		if(isInvincible)
			g.setColor(color_invincible);
		else
			g.setColor(color);
		Ellipse2D e = (Ellipse2D.Float) shape;
		e.setFrame(x - r, y - r, 2.0f * r, 2.0f * r);
		g.fill(e);
	}

	public void setDirection(int direction) {
		switch(direction) {
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		default:
			break;
		}
	}

	public void removeDirection(int direction) {
		switch(direction) {
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		default:
			break;
		}
	}

	@Override
	public void move() {
		if(up)
			y -= speed;
		if(down)
			y += speed;
		if(left)
			x -= speed;
		if(right)
			x += speed;

		if(x < 0)
			x = 0;
		else if(x > GamePanel.PPANEL_WIDTH)
			x = GamePanel.PPANEL_WIDTH;

		if(y < 0)
			y = 0;
		else if(y > GamePanel.PPANEL_HEIGHT)
			y = GamePanel.PPANEL_HEIGHT;

		if(!focus) {
			theta += (float) Math.PI / 50f;
			if(theta > 2.0f * (float) Math.PI)
				theta -= 2.0f * (float) Math.PI;
		}
	}

	public float getTheta() {
		return theta;
	}

	public boolean isAlive() {
		return isAlive;
	}

	class MPRecover extends Thread {
		public MPRecover() {
			pPanel.getThreads().add(this);
		}

		@Override
		public void run() {
			try {
				while(!Thread.currentThread().isInterrupted())
					if(!pPanel.isPaused()) {
						if(mp < max_mp)
							mp += mpInc + 0.2f * level;
						if(mp > max_mp)
							mp = max_mp;

						Thread.sleep(MainFrame.frameInterval * 10);
					}
			} catch(InterruptedException e) {
			} finally {
				System.out.println("MPRecover end");
			}
		}
	}

	public void setFocusAttack() {
		focus = true;
	}

	public void removeFocusAttack() {
		focus = false;
	}

	public boolean collisionCheck(Unit e) {
		if(isInvincible)
			if(!bulletReflecting)
				return false;

		float dx = e.getX() - x;
		float dy = e.getY() - y;
		float d = dx * dx + dy * dy;

		boolean collision = d <= (r + e.getR()) * (r + e.getR());

		if(bulletReflecting)
			if(e instanceof Bullet) {
				if(collision) {
					pPanel.getEnemybullets().remove(e);
					((Bullet) e).reflect2D(new float[] { x - e.getX(), y - e.getY() });
					bullets.add((Bullet) e);
				}
				return false;
			}
		return !isInvincible && collision;
	}

	public float getMp() {
		return mp;
	}

	public float getMaxMp() {
		return max_mp;
	}

	public void addMP(float mpInc) {
		mp += mpInc;
	}

	public boolean useMP(float mpUse) {
		if(mp >= mpUse) {
			mp -= mpUse;
			return true;
		}
		return false;
	}

	public float getExp() {
		return exp;
	}

	public float getMaxExp() {
		return max_exp;
	}

	public void addExp(int expInc) {
		exp += expInc;
		if(exp >= max_exp)
			levelUp();
	}

	public void die() {
		isAlive = false;
	}

	public void levelUp() {
		level++;
		max_exp = 30.0f * (float) Math.pow(level, 1.2f);
		max_mp = 50.0f + 50.0f * level;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public boolean isInvincible() {
		return isInvincible;
	}

	@Override
	public void setInvincible(boolean invincible) {
		isInvincible = invincible;
	}

	public boolean isReflectingBullet() {
		return bulletReflecting;
	}

	public void setReflectingBullet(boolean bulletReflecting) {
		this.bulletReflecting = bulletReflecting;
	}

	public void addSkill(SkillKeys key, Skill skill) {
		skills.put(key, skill);
	}

	public void useSkill(SkillKeys key) {
		Skill s = skills.get(key);
		if(s != null)
			s.useSkill();
	}

	public PlayPanel_Original getPPanel() {
		return pPanel;
	}
}
