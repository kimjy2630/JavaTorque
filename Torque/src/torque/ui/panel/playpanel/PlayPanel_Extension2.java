package torque.ui.panel.playpanel;

import java.util.*;

import torque.ui.panel.*;
import torque.unit.enemy.*;

public class PlayPanel_Extension2 extends PlayPanel_Extension1 {

	public PlayPanel_Extension2(GamePanel gPanel) {
		super(gPanel);
	}

	@Override
	protected void createEnemy() {
		int c;

		c = (int) (new Random().nextInt(levelNum) * 1.5f);
		if(c == 0)
			new EnemyGeneratingCircle(new RedEnemy(PlayPanel_Extension2.this));

		c = new Random().nextInt(levelNum * 2);
		if(c == 0)
			new EnemyGeneratingCircle(new PurpleEnemy(PlayPanel_Extension2.this));

		c = new Random().nextInt((int) (levelNum * levelNum2 * 0.8f));
		if(c == 0)
			new EnemyGeneratingCircle(new BlueEnemy(PlayPanel_Extension2.this));

		c = new Random().nextInt(levelNum * (int) (levelNum2 * 11.0f / 9.0f));
		if(c == 0)
			new EnemyGeneratingCircle(new GreenEnemy(PlayPanel_Extension2.this));

		c = new Random().nextInt(levelNum * levelNum2 * 2);
		if(c == 0)
			new EnemyGeneratingCircle(new BrownEnemy(PlayPanel_Extension2.this));

		c = new Random().nextInt(levelNum * (int) (levelNum2 * 11.0f / 5.0f));
		if(c == 0)
			new EnemyGeneratingCircle(new BasicAttackEnemy(PlayPanel_Extension2.this));

		c = new Random().nextInt(levelNum * (int) (levelNum2 * 13.0f / 5.0f));
		if(c == 0)
			new EnemyGeneratingCircle(new TwoBulletBasicEnemy(PlayPanel_Extension2.this));

		levelNum = Math.max(10, (int) Math.ceil(60.0f - (float) score * 0.002f));
		levelNum2 = Math.max(3, (int) Math.ceil(10.0f - (float) score * 0.0005f));
	}

}
