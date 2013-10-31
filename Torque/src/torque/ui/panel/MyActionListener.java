package torque.ui.panel;

import java.awt.event.*;

import torque.ui.frame.*;

public class MyActionListener implements ActionListener {
	private static final MyActionListener actionListener = new MyActionListener();
	private static MainFrame mainFrame = null;

	public static void initialize(MainFrame mainFrame) {
		MyActionListener.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch(command) {
		case "START":
			MainFrame.mainFrame.showPanel("select");
			break;
		case "ORIGINAL":
			MainFrame.mainFrame.showPanel("game");
			MainFrame.mainFrame.startGame(GamePanel.MODE_ORIGINAL);
			break;
		case "EXTENSION1":
			MainFrame.mainFrame.showPanel("game");
			MainFrame.mainFrame.startGame(GamePanel.MODE_EXTENSTION1);
			break;
		case "EXTENSION2":
			MainFrame.mainFrame.showPanel("game");
			MainFrame.mainFrame.startGame(GamePanel.MODE_EXTENSION2);
			break;
		case "EXTENSION3":
			MainFrame.mainFrame.showPanel("game");
			MainFrame.mainFrame.startGame(GamePanel.MODE_EXTENSION3);
			break;
		case "HELP":
			MainFrame.mainFrame.showPanel("help");
			break;
		case "LOGOUT":
			MainFrame.mainFrame.id = null;
			MainFrame.mainFrame.showPanel("login");
			break;
		case "RETURN":
		case "MAIN MENU":
			MainFrame.mainFrame.showPanel("menu");
			break;
		case "EXIT":
			System.out.println("exit");
			System.exit(0);
			break;
		case "REPLAY":
			MainFrame.mainFrame.showPanel("game");
			MainFrame.mainFrame.restartGame();
			break;
		}
	}

	public static MyActionListener getActionListener() {
		return actionListener;
	}

}
