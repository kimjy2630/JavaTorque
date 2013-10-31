package torque.unit.skills;

import torque.unit.player.*;

public abstract class Skill {
	protected int levelLimit;
	protected int mpUse;

	protected Player player;

	public Skill(int levelLimit, int mpUse) {
		this.levelLimit = levelLimit;
		this.mpUse = mpUse;
	}

	public Skill(int levelLimit, int mpUse, Player player) {
		this(levelLimit, mpUse);
		this.player = player;
	}

	public abstract void useSkill();
}
