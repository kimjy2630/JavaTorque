package torque.unit.enemy;

import torque.ui.panel.playpanel.*;
import torque.unit.abilities.*;

public abstract class AttackableEnemy extends Enemy implements Attackable {

	protected AttackableEnemy(PlayPanel pPanel) {
		super(pPanel);
	}
}
