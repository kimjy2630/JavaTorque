package torque.unit.enemy;

import java.awt.*;
import java.awt.geom.*;

import torque.ui.panel.*;
import torque.ui.panel.playpanel.*;

public class PurpleEnemy extends Enemy {
	public static final Color color = new Color(0xC000A0);

	private float phi; // 회전 각도

	float[] xs = new float[3];
	float[] ys = new float[3];

	public PurpleEnemy(PlayPanel pPanel) {
		super(pPanel);
		
		name = "Purple Enemy";
		
		health = 1.0f;
		// speed = 1.85;
		speed = 2.5f;
		score = 58;
		exp = 2.2f;
		phi = (float) Math.random() * (float) Math.PI * 2.0f;

		r = 7.0f;
		x = (float) Math.random() * ((float) GamePanel.PPANEL_WIDTH - r);
		y = (float) Math.random() * ((float) GamePanel.PPANEL_HEIGHT - r);

		shape = new GeneralPath();
	}

	@Override
	public void move() {
		super.move();

		phi += (float) Math.PI / 40.0f;
		if(phi > (float) Math.PI * 2.0f)
			phi -= (float) Math.PI * 2.0f;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);

		for(int i = 0; i < 3; i++) {
			xs[i] = x + 1.2f * r * (float) Math.sin(phi + (float) i * (float) Math.PI * 2.0f / 3.0f);
			ys[i] = y + 1.2f * r * (float) Math.cos(phi + (float) i * (float) Math.PI * 2.0f / 3.0f);
		}

		Path2D p = (GeneralPath) shape;
		p.reset();
		p.moveTo(xs[0], ys[0]);
		for(int i = 0; i < 3; i++)
			p.lineTo(xs[i], ys[i]);
		p.closePath();

		g.fill(p);

		// g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 *
		// r)); // 실제 타격범위 표시
	}
}
