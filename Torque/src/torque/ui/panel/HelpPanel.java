package torque.ui.panel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class HelpPanel extends JPanel {
	JButton btn_return = new JButton("RETURN");

	MyActionListener actionListener = null;

	public HelpPanel() {
		actionListener = MyActionListener.getActionListener();

		setBackground(Color.GREEN);
		setLayout(null);

		// btn_return.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width
		// / 2 - 50, 400, 100, 50);
		btn_return.setBounds(GamePanel.PPANEL_WIDTH / 2 - 50, 400, 100, 50);

		btn_return.addActionListener(actionListener);

		btn_return.setMnemonic(KeyEvent.VK_R);

		add(btn_return);
	}

}
