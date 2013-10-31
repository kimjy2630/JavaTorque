package torque.unit.skills;

import torque.unit.bullet.*;
import torque.unit.player.*;

public class FireBall extends Skill {

	public FireBall(Player player) {
		super(0, 0, player);
	}

	@Override
	public void useSkill() {
		if(player.getLevel() >= levelLimit)
			if(player.useMP(mpUse)) {
				float x = player.getX();
				float y = player.getY();
				float r = player.getR();
				float theta = player.getTheta();
				player.getBullets().add(new FireBallBullet(x + r / 2.0f * (float) Math.cos(theta), y + r / 2.0f * (float) Math.sin(theta), theta));
			}
	}
}
