package torque.unit.skills;

import java.awt.event.*;

public enum SkillKeys {
	Z(KeyEvent.VK_Z), X(KeyEvent.VK_X), C(KeyEvent.VK_C), V(KeyEvent.VK_V), A(KeyEvent.VK_A);

	int key;

	SkillKeys(int key) {
		this.key = key;
	}
}
