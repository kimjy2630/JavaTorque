package torque.unit.skills;

import torque.unit.bullet.*;
import torque.unit.player.*;

public class BulletRadiation extends Skill {

	public BulletRadiation(Player player) {
		super(2, 30, player);
	}

	@Override
	public void useSkill() {
		if(player.getLevel() >= levelLimit)
			if(player.useMP(mpUse))
				for(int i = 0; i < 180; i++) {
					float x = player.getX();
					float y = player.getY();
					float r = player.getR();
					float theta = player.getTheta();
					player.getBullets().add(new Bullet(x + r / 2.0f * (float) Math.cos(theta), y + r / 2.0f * (float) Math.sin(theta), (float) i * (float) Math.PI / 90.0f));
				}
	}

}
