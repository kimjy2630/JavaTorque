package torque.ui.frame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import torque.ui.panel.*;

/**
 * Main frame of the game.
 * 
 * @author ¿Á¿±
 * 
 */
public class MainFrame extends JFrame {

	public static MainFrame mainFrame;
	/**
	 * The interval in millisecond between torque.ui.frame.
	 */
	public static int frameInterval = 15;

	CardLayout cl = null;

	ModeSelectionPanel sPanel = null;
	GamePanel gPanel = null;
	HelpPanel hPanel = null;
	MenuPanel mPanel = null;
	ReplayPanel rPanel = null;
	LoginPanel lPanel = null;

	MyWindowListener windowListener = new MyWindowListener();

	public String id = "default";

	public MainFrame() {
		MyActionListener.initialize(this);

		cl = new CardLayout();

		sPanel = new ModeSelectionPanel();
		gPanel = new GamePanel();
		hPanel = new HelpPanel();
		mPanel = new MenuPanel();
		rPanel = new ReplayPanel();
		lPanel = new LoginPanel();

		setTitle("Torque");

		setLayout(cl);
		add("select", sPanel);
		add("menu", mPanel);
		add("help", hPanel);
		add("game", gPanel);
		add("replay", rPanel);
		add("login", lPanel);

		addWindowListener(windowListener);

		showPanel("login");

		// setUndecorated(true);
		// setSize(GamePanel.PPANEL_WIDTH + 16, GamePanel.PPANEL_HEIGHT + 50 + 38);
		pack();
		setVisible(true);

		// GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
	}

	public static void main(String[] args) {
		mainFrame = new MainFrame();
	}

	class MyWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	/**
	 * Get the instance of GamePanel.
	 * 
	 * @return pPanel
	 */
	public GamePanel getGPanel() {
		return gPanel;
	}

	public void replay() {

	}

	/**
	 * Finish game. Submit final score and show MenuPanel.
	 * 
	 * @param score
	 *            high score
	 */
	public void gameOver() {
		showPanel("replay");
		System.out.println("gameover");
		System.gc();
	}

	public void showPanel(String name) {
		cl.show(getContentPane(), name);
	}

	public void startGame(int mode) {
		gPanel.startGame(mode);
	}

	public void restartGame() {
		gPanel.restartGame();
	}

	public ReplayPanel getRPanel() {
		return rPanel;
	}
}
