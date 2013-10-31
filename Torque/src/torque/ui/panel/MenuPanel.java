package torque.ui.panel;

import java.awt.event.*;

import javax.swing.*;

public class MenuPanel extends JPanel {

	JButton btn_start = new JButton("START");
	JButton btn_help = new JButton("HELP");
	JButton btn_logout = new JButton("LOGOUT");
	JButton btn_exit = new JButton("EXIT");

	JLabel lbl_logo = new JLabel("TORQUE", JLabel.CENTER);

	ActionListener actionListener = null;

	public MenuPanel() {
		actionListener = MyActionListener.getActionListener();

		setLayout(null);

		// lbl_logo.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width
		// / 2 - 150, 100, 300, 100);
		lbl_logo.setBounds(GamePanel.PPANEL_WIDTH / 2 - 150, 100, 300, 100);

		btn_start.addActionListener(actionListener);
		btn_help.addActionListener(actionListener);
		btn_logout.addActionListener(actionListener);
		btn_exit.addActionListener(actionListener);

		// btn_start.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width
		// / 2 - 50, 300, 100, 50);
		btn_start.setBounds(GamePanel.PPANEL_WIDTH / 2 - 50, 300, 100, 50);
		// btn_help.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width
		// / 2 - 50, 360, 100, 50);
		btn_help.setBounds(GamePanel.PPANEL_WIDTH / 2 - 50, 360, 100, 50);
		// btn_logout.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width
		// / 2 - 50, 420, 100, 50);
		btn_logout.setBounds(GamePanel.PPANEL_WIDTH / 2 - 50, 420, 100, 50);
		// btn_exit.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width
		// / 2 - 50, 480, 100, 50);
		btn_exit.setBounds(GamePanel.PPANEL_WIDTH / 2 - 50, 480, 100, 50);

		btn_start.setMnemonic(KeyEvent.VK_S);
		btn_help.setMnemonic(KeyEvent.VK_H);
		btn_logout.setMnemonic(KeyEvent.VK_L);
		btn_exit.setMnemonic(KeyEvent.VK_E);

		add(lbl_logo);
		add(btn_start);
		add(btn_help);
		add(btn_logout);
		add(btn_exit);
	}

}
