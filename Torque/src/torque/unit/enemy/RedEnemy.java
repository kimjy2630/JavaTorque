package torque.unit.enemy;

import java.awt.*;
import java.awt.geom.*;

import torque.ui.panel.*;
import torque.ui.panel.playpanel.*;

public class RedEnemy extends Enemy {
	public static final Color color = Color.RED;

	public RedEnemy(PlayPanel pPanel) {
		super(pPanel);
		
		name = "Red Enemy";

		health = 1.0f;
		// speed = 1;
		speed = 1.5f;
		score = 29;
		exp = 1.0f;

		r = 7.0f;
		x = (float) Math.random() * ((float) GamePanel.PPANEL_WIDTH - r);
		y = (float) Math.random() * ((float) GamePanel.PPANEL_HEIGHT - r);

		shape = new Ellipse2D.Float(x - r, y - r, 2.0f * r, 2.0f * r);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		Ellipse2D e = (Ellipse2D.Float) shape;
		e.setFrame(x - r, y - r, 2.0 * r, 2.0 * r);
		g.draw(e);
	}

}
