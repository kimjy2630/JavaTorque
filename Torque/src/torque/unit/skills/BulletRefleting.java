package torque.unit.skills;

import torque.unit.player.*;

public class BulletRefleting extends Skill {

	public BulletRefleting(Player player) {
		super(8, 20, player);
	}

	@Override
	public void useSkill() {
		if(player.isReflectingBullet())
			return;
		if(player.getLevel() >= levelLimit)
			if(player.useMP(mpUse))
				new Thread() {
					@Override
					public void run() {
						player.getPPanel().getThreads().add(this);
						player.setReflectingBullet(true);
						try {
							Thread.sleep(10000);
						} catch(InterruptedException e) {
						}
						player.setReflectingBullet(false);
					}
				}.start();
	}
}
