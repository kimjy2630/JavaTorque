package torque.unit.skills;

import torque.unit.player.*;

public class InvincibleSkill extends Skill {

	public InvincibleSkill(Player player) {
		super(6, 15, player);
	}

	@Override
	public void useSkill() {
		if(player.isInvincible())
			return;
		if(player.getLevel() >= levelLimit)
			if(player.useMP(mpUse))
				new Thread() {
					@Override
					public void run() {
						player.getPPanel().getThreads().add(this);
						player.setInvincible(true);
						try {
							Thread.sleep(10000);
						} catch(InterruptedException e) {
						}
						player.setInvincible(false);
					}
				}.start();
	}
}
