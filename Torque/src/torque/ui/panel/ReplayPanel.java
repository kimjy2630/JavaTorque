package torque.ui.panel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import torque.ui.frame.*;

public class ReplayPanel extends JPanel {
	JLabel lbl_finalScore = new JLabel("", JLabel.CENTER);
	JButton btn_replay = new JButton("REPLAY");
	JButton btn_toMainMenu = new JButton("MAIN MENU");
	JButton btn_exit = new JButton("EXIT");

	ScoreSender scoreSender = null;

	MyActionListener actionListener = null;

	public ReplayPanel() {
		actionListener = MyActionListener.getActionListener();

		setLayout(null);
		setSize(GamePanel.PPANEL_WIDTH, GamePanel.PPANEL_HEIGHT + 50);
		setBackground(Color.MAGENTA);

		// lbl_finalScore.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 50, 200, 100, 50);
		lbl_finalScore.setBounds(GamePanel.PPANEL_WIDTH / 2 - 50, 200, 100, 50);
		// btn_replay.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 50, 300, 100, 50);
		btn_replay.setBounds(GamePanel.PPANEL_WIDTH / 2 - 50, 300, 100, 50);
		// btn_toMainMenu.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 50, 360, 100, 50);
		btn_toMainMenu.setBounds(GamePanel.PPANEL_WIDTH / 2 - 50, 360, 100, 50);
		// btn_exit.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 50, 420, 100, 50);
		btn_exit.setBounds(GamePanel.PPANEL_WIDTH / 2 - 50, 420, 100, 50);

		btn_replay.addActionListener(actionListener);
		btn_toMainMenu.addActionListener(actionListener);
		btn_exit.addActionListener(actionListener);

		btn_replay.setMnemonic(KeyEvent.VK_R);
		btn_toMainMenu.setMnemonic(KeyEvent.VK_M);
		btn_exit.setMnemonic(KeyEvent.VK_E);

		add(lbl_finalScore);
		add(btn_replay);
		add(btn_toMainMenu);
		add(btn_exit);

	}

	public void gameOver(int score, Thread t) {
		submitScore(score, t);
		lbl_finalScore.setText("Score : " + score);
		t.interrupt();
	}

	public void submitScore(int score, final Thread t) {
		scoreSender = new ScoreSender(score);
		scoreSender.start();
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch(InterruptedException ie) {
				} finally {
					if(scoreSender != null) {
						if(!scoreSender.sendSuccess) {
							scoreSender.interrupt();
							System.out.println("서버 연결 실패");
							JOptionPane.showMessageDialog(ReplayPanel.this, "서버 연결 실패");
						}
					}
					t.interrupt();
					MainFrame.mainFrame.gameOver();
				}
			}
		}.start();
	}

	public class ScoreSender extends Thread {
		int score;

		Socket client = null;

		Scanner scanner = null;
		PrintWriter pw = null;

		String server_ip = null;

		static final int port = 5029;

		JDialog dialog_sendSuccess = null;
		JButton btn_dialog = null;

		boolean sendSuccess = false;

		public ScoreSender(int score) {
			this.score = score;
			server_ip = "127.0.0.1";
			dialog_sendSuccess = new JDialog(MainFrame.mainFrame);
		}

		@Override
		public void run() {
			try {
				client = new Socket(server_ip, port);

				InputStream is = client.getInputStream();
				OutputStream os = client.getOutputStream();

				scanner = new Scanner(is);
				pw = new PrintWriter(os);

				String msg = "Score#" + MainFrame.mainFrame.id + "#" + score + "#end#";

				pw.println(msg);
				pw.flush();

				while(!sendSuccess && !isInterrupted()) {
					// System.out.println("Aaa");
					if(scanner.hasNextLine()) {
						System.out.println("bb");
						msg = scanner.nextLine();
						msg = msg.trim();
						System.out.println(msg);
						if(msg.equals("SUCCESS")) {
							System.out.println("CCC");
							scanner.close();
							pw.close();
							// rPanel에 성공 메세지 출력

							// dialog_sendSuccess.setTitle("Success");
							// dialog_sendSuccess.add(btn_dialog);
							sendSuccess = true;
							System.out.println("EEE");
							break;
						}
					}
				}
				System.out.println("DDD");
			} catch(IOException e) {
				System.out.println(e.getMessage());

				// dialog_sendSuccess.setTitle("서버와의 연결이 끊어졌습니다.\n");
				JOptionPane.showMessageDialog(ReplayPanel.this, "서버와의 연결이 끊어졌습니다.\n");
			}
		}
	}
}
