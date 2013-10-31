package torque.ui.panel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import torque.ui.frame.*;

/**
 * The panel with login.
 * 
 * @author 재윤
 * 
 */
public class LoginPanel extends JPanel {

	JLabel lbl_logo = new JLabel("TORQUE", JLabel.CENTER);
	JLabel lbl_id = new JLabel("ID : ");
	JLabel lbl_password = new JLabel("PASSWORD : ");

	JTextField txt_id = new JTextField(8);
	JPasswordField txt_password = new JPasswordField();

	JButton btn_login = new JButton("Login");
	JButton btn_exit = new JButton("EXIT");

	JDialog dia_alert_ID = null;

	LoginActionListener actionListener = new LoginActionListener();

	/**
	 * Constructs new instance of LoginPanel whose components are initialized and added.
	 */
	public LoginPanel() {
		setBackground(Color.ORANGE);
		setLayout(null);

		// lbl_logo.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 150, 100, 300, 100);
		lbl_logo.setBounds(GamePanel.PPANEL_WIDTH / 2 - 150, 100, 300, 100);
		// lbl_id.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 110, 300, 100, 20);
		lbl_id.setBounds(GamePanel.PPANEL_WIDTH / 2 - 110, 300, 100, 20);
		// lbl_password.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 110, 330, 100, 20);
		lbl_password.setBounds(GamePanel.PPANEL_WIDTH / 2 - 110, 330, 100, 20);

		// txt_id.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 30, 300, 80, 20);
		txt_id.setBounds(GamePanel.PPANEL_WIDTH / 2 - 30, 300, 80, 20);
		// txt_password.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 30, 330, 80, 20);
		txt_password.setBounds(GamePanel.PPANEL_WIDTH / 2 - 30, 330, 80, 20);

		// btn_login.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 + 60, 300, 70, 50);
		btn_login.setBounds(GamePanel.PPANEL_WIDTH / 2 + 60, 300, 70, 50);
		// btn_exit.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 50, 480, 100, 50);
		btn_exit.setBounds(GamePanel.PPANEL_WIDTH / 2 - 50, 480, 100, 50);

		txt_id.addActionListener(actionListener);
		txt_password.addActionListener(actionListener);

		btn_login.addActionListener(actionListener);
		btn_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("exit");
				System.exit(0);
			}
		});

		btn_login.setMnemonic(KeyEvent.VK_L);
		btn_exit.setMnemonic(KeyEvent.VK_E);

		add(lbl_logo);
		add(lbl_id);
		add(lbl_password);
		add(txt_id);
		add(txt_password);
		add(btn_login);
		add(btn_exit);
	}

	class LoginActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(txt_id.getText().length() == 0) {
				dia_alert_ID = new JDialog(MainFrame.mainFrame, true);
				dia_alert_ID.setUndecorated(true);
				dia_alert_ID.setBounds(GamePanel.PPANEL_WIDTH / 2 - 100, 300, 200, 100);
				dia_alert_ID.setLayout(null);

				JLabel lbl_dia_alert = new JLabel("ID를 입력해주십시오");
				JButton btn_dia_ok = new JButton("OK");

				lbl_dia_alert.setBounds(40, 20, 180, 30);
				btn_dia_ok.setBounds(70, 70, 60, 20);

				btn_dia_ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dia_alert_ID.dispose();
						dia_alert_ID = null;
					}
				});

				btn_dia_ok.setMnemonic(KeyEvent.VK_O);

				dia_alert_ID.add(lbl_dia_alert);
				dia_alert_ID.add(btn_dia_ok);

				dia_alert_ID.setVisible(true);
				return;
			} else {
				MainFrame.mainFrame.id = txt_id.getText();
				MainFrame.mainFrame.showPanel("menu");
				txt_id.setText("");
				txt_password.setText("");
			}
		}
	}
}
