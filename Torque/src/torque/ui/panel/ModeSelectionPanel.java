package torque.ui.panel;

import java.awt.event.*;

import javax.swing.*;

public class ModeSelectionPanel extends JPanel {
	JLabel lbl_modeSelect = null;

	JButton btn_mode_original = null;
	JButton btn_mode_extension1 = null;
	JButton btn_mode_extension2 = null;
	JButton btn_mode_extension3 = null;
	JButton btn_mainmenu = null;

	ActionListener actionListener = null;

	public ModeSelectionPanel() {
		setLayout(null);

		actionListener = MyActionListener.getActionListener();

		lbl_modeSelect = new JLabel("Select Game Mode");

		btn_mode_original = new JButton("ORIGINAL");
		btn_mode_extension1 = new JButton("EXTENSION1");
		btn_mode_extension2 = new JButton("EXTENSION2");
		btn_mode_extension3 = new JButton("EXTENSION3");
		btn_mainmenu = new JButton("MAIN MENU");

		lbl_modeSelect.setBounds(100, 50, 300, 50);
		btn_mode_original.setBounds(100, 150, 120, 50);
		btn_mode_extension1.setBounds(230, 150, 120, 50);
		btn_mode_extension2.setBounds(360, 150, 120, 50);
		btn_mode_extension3.setBounds(490, 150, 120, 50);
		btn_mainmenu.setBounds(GamePanel.PPANEL_WIDTH / 2 - 50, 400, 100, 50);

		btn_mode_original.addActionListener(actionListener);
		btn_mode_extension1.addActionListener(actionListener);
		btn_mode_extension2.addActionListener(actionListener);
		btn_mode_extension3.addActionListener(actionListener);
		btn_mainmenu.addActionListener(actionListener);

		btn_mode_original.setMnemonic(KeyEvent.VK_O);
		btn_mode_extension1.setMnemonic(KeyEvent.VK_E);
		btn_mode_extension2.setMnemonic(KeyEvent.VK_X);
		btn_mode_extension3.setMnemonic(KeyEvent.VK_T);
		btn_mainmenu.setMnemonic(KeyEvent.VK_M);

		add(lbl_modeSelect);
		add(btn_mode_original);
		add(btn_mode_extension1);
		add(btn_mode_extension2);
		add(btn_mode_extension3);
		add(btn_mainmenu);
	}
}
