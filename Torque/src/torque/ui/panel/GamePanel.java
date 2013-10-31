package torque.ui.panel;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import torque.ui.panel.playpanel.*;
import torque.unit.enemy.*;
import torque.unit.player.*;

/**
 * The class in which the game is played. This class has <code>PlayPanel_Original and <code>StatusPanel in it.
 * 
 * @author ¿Á¿±
 * 
 */
public class GamePanel extends JPanel {
	/**
	 * Height of play panel.
	 */
	public static int PPANEL_HEIGHT = 540;
	// public static int PPANEL_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height - 50;
	/**
	 * Width of play panel.
	 */
	public static int PPANEL_WIDTH = 720;
	// public static int PPANEL_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;

	public static final int MODE_ORIGINAL = 1;
	public static final int MODE_EXTENSTION1 = 2;
	public static final int MODE_EXTENSION2 = 3;
	public static final int MODE_EXTENSION3 = 4;

	private ArrayList<Image> bgImg = new ArrayList<>();

	private PlayPanel pPanel;
	private StatusPanel sPanel;

	private int mode;

	/**
	 * Constructs an object. Components are not initialized yet.
	 */
	public GamePanel() {
		// setBackground(Color.BLUE);

		setPreferredSize(new Dimension(GamePanel.PPANEL_WIDTH, GamePanel.PPANEL_HEIGHT + 50));
		setDoubleBuffered(true);
		setLayout(null);

		sPanel = new StatusPanel();
		add(sPanel);
	}

	private void bgInitialize() {
		Image bg;
		Graphics2D g;

		bg = createImage(PPANEL_WIDTH, PPANEL_HEIGHT);
		g = (Graphics2D) bg.getGraphics();

		g.setColor(Color.BLACK);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillRect(0, 0, PPANEL_WIDTH, PPANEL_HEIGHT);
		bgImg.add(bg);

		bg = createImage(PPANEL_WIDTH, PPANEL_HEIGHT);
		g = (Graphics2D) bg.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
		g.setPaint(new GradientPaint(PPANEL_WIDTH / 3.0f, PPANEL_HEIGHT / 3.0f, Color.RED, PPANEL_WIDTH * 2.0f / 3.0f, PPANEL_HEIGHT * 2.0f / 3.0f, Color.BLUE));
		g.fillRect(0, 0, PPANEL_WIDTH, PPANEL_HEIGHT);
		g.setPaint(new GradientPaint(PPANEL_WIDTH * 2.0f / 3.0f, PPANEL_HEIGHT / 3.0f, Color.GREEN, PPANEL_WIDTH / 3.0f, PPANEL_HEIGHT * 2.0f / 3.0f, PurpleEnemy.color));
		g.fillRect(0, 0, PPANEL_WIDTH, PPANEL_HEIGHT);
		g.setComposite(AlphaComposite.Src);
		bgImg.add(bg);

		bg = createImage(PPANEL_WIDTH, PPANEL_HEIGHT);
		g = (Graphics2D) bg.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
		g.setPaint(new GradientPaint(PPANEL_WIDTH / 3.0f, PPANEL_HEIGHT / 3.0f, Color.RED, PPANEL_WIDTH * 2.0f / 3.0f, PPANEL_HEIGHT * 2.0f / 3.0f, Color.BLUE));
		g.fillRect(0, 0, PPANEL_WIDTH, PPANEL_HEIGHT);
		g.setPaint(new GradientPaint(PPANEL_WIDTH * 2.0f / 3.0f, PPANEL_HEIGHT / 3.0f, Color.GREEN, PPANEL_WIDTH / 3.0f, PPANEL_HEIGHT * 2.0f / 3.0f, PurpleEnemy.color));
		g.fillRect(0, 0, PPANEL_WIDTH, PPANEL_HEIGHT);
		bgImg.add(bg);
	}

	/**
	 * Initialize components in this object and starts game.
	 */
	public void startGame(int mode) {
		if(pPanel != null)
			remove(pPanel);

		if(bgImg.size() == 0)
			bgInitialize();

		sPanel.initText();

		this.mode = mode;
		switch(mode) {
		case MODE_ORIGINAL:
			pPanel = new PlayPanel_Original(this);
			break;
		case MODE_EXTENSTION1:
			pPanel = new PlayPanel_Extension1(this);
			break;
		case MODE_EXTENSION2:
			pPanel = new PlayPanel_Extension2(this);
			break;
		case MODE_EXTENSION3:
			pPanel = new PlayPanel_Extension3(this);
			break;
		}

		add(pPanel);

		pPanel.startGame();
	}

	public void restartGame() {
		startGame(mode);
	}

	/**
	 * A panel that shows status of game.
	 * 
	 * @author ¿Á¿±
	 */
	public class StatusPanel extends JPanel {

		private JLabel lbl_score = null;
		private JLabel lbl_mp_exp = null;
		private JLabel lbl_level = null;

		public StatusPanel() {
			setBackground(Color.GRAY);
			setLayout(new BorderLayout());

			// setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - PPANEL_WIDTH / 2, 0, PPANEL_WIDTH, 50);
			setBounds(0, 0, PPANEL_WIDTH, 50);

			initButton();

			add(lbl_score, "West");
			add(lbl_mp_exp, "East");
			add(lbl_level, "Center");
		}

		private void initButton() {
			lbl_score = new JLabel("Score : 0");
			lbl_mp_exp = new JLabel("mp 0/0\nexp 0/0");
			lbl_level = new JLabel("Lv.1");
		}

		public void initText() {
			lbl_score.setText("Score : 0");
			lbl_mp_exp.setText("mp 0/0\nexp 0/0");
			lbl_level.setText("Lv.1");
		}

		public void refreshText(Player player) {
			lbl_mp_exp.setText("mp " + (int) player.getMp() + "/" + player.getMaxMp() + "\nexp " + (int) player.getExp() + "/" + (int) player.getMaxExp());
			lbl_level.setText("Lv." + player.getLevel());
		}
	}

	/*
	 * static { if(PPANEL_WIDTH > 4.0 / 3.0 * PPANEL_HEIGHT) { PPANEL_WIDTH = (int) (4.0 / 3.0 * PPANEL_HEIGHT); } }
	 */

	public PlayPanel getPPanel() {
		return pPanel;
	}

	public ArrayList<Image> getBGImg() {
		return bgImg;
	}

	public void setScore(int score) {
		sPanel.lbl_score.setText("Score : " + score);
	}

	public StatusPanel getSPanel() {
		return sPanel;
	}
}
