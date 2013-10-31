package torque.unit.skills;

import torque.unit.bullet.*;
import torque.unit.player.*;

public class AutoMissile extends Skill {

	public AutoMissile(Player player) {
		super(4, 10, player);
	}

	@Override
	public void useSkill() {
		if(player.getLevel() >= levelLimit)
			if(player.useMP(mpUse))
				for(int i = 0; i < 3; i++) {
					float x = player.getX();
					float y = player.getY();
					float r = player.getR();
					float theta = player.getTheta();
					player.getBullets().add(new GuidedBullet(x + r / 2.0f * (float) Math.cos(theta), y + r / 2.0f * (float) Math.sin(theta), (float) i * 2.0f * (float) Math.PI / 3.0f, i, player.getPPanel()));
				}
	}

}
