package torque.unit.enemy;

import java.awt.*;
import java.awt.geom.*;

import torque.ui.panel.*;
import torque.ui.panel.playpanel.*;

public class BrownEnemy extends Enemy {

	public static final Color color = new Color(0xC0C080);

	private float phi = 0.0f;
	float[] xs = new float[8];
	float[] ys = new float[8];

	public BrownEnemy(PlayPanel pPanel) {
		super(pPanel);
		
		name = "Brown Enemy";
		
		health = 100.0f;
		// speed = 0.8;
		speed = 1.2f;
		score = 73;
		exp = 3.0f;

		r = 13.0f;
		x = (float) Math.random() * ((float) GamePanel.PPANEL_WIDTH - r);
		y = (float) Math.random() * ((float) GamePanel.PPANEL_HEIGHT - r);

		shape = new GeneralPath();
	}

	@Override
	public void move() {
		super.move();

		phi += (float) Math.PI / 80.0f;
		if(phi > (float) Math.PI * 2.0f)
			phi -= (float) Math.PI * 2.0f;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);

		for(int i = 0; i < 8; i++) {
			xs[i] = x + 1.2f * r * (float) Math.sin(phi + (float) i * (float) Math.PI / 4.0f);
			ys[i] = y + 1.2f * r * (float) Math.cos(phi + (float) i * (float) Math.PI / 4.0f);
		}

		GeneralPath p = (GeneralPath) shape;
		p.reset();
		p.moveTo(xs[0], ys[0]);
		for(int i = 0; i < 8; i++)
			p.lineTo(xs[i], ys[i]);
		p.closePath();

		g.fill(p);

		// g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 *
		// r)); // 실제 타격범위 표시
	}
}
