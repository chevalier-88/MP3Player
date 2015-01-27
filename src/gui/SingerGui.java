package gui;

import handle.SingerListener;

import java.awt.Color;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

public class SingerGui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SingerGui.class);
	/**
	 * Here announces variables for gui components
	 */
	// menuBar:
	private JMenuBar topMenuBar;

	private JMenu menuFile;
	private JMenuItem itemOpen;
	private JMenuItem itemClear;
	private JMenuItem itemClose;
	private JMenu menuService;
	private JMenuItem itemAbout;

	private JMenu itemMenuSkins;
	private ButtonGroup btnGroup;
	private JRadioButton rbtnSkinModern;
	private JRadioButton rbtnSkinClassic;

	// URL:
	private URL urlPlay;
	private URL urlPause;
	private URL urlStop;
	private URL urlPrev;
	private URL urlNext;
	private URL urlAdd;
	private URL urlVolume;

	// buttons:
	private JButton btnPlay;
	private JButton btnPause;
	private JButton btnStop;
	private JButton btnPrev;
	private JButton btnNext;
	private JButton btnAdd;
	private JButton btnDel;
	private JToggleButton togBtnVolume;

	// popupMenu:
	private JPopupMenu functionalPopMenu;
	private JMenuItem itemPopPlay;
	private JMenuItem itemPopPause;
	private JMenuItem itemPopStop;
	private JMenuItem itemPopAdd;
	private JMenuItem itemPopDel;

	// other:
	private JLabel lblVolume;
	private JLabel lblTimeSong;
	private JLabel lblNameSong;
	private JSlider slVolume;
	private JSlider slProgressSong;
	private JScrollPane scrPanelList;
	private JList<String> playList;

	private JPanel panel;

	private static SingerGui player;

	private SingerGui() {
		logger.info("creating object of SingerGui class");
	}

	public JFrame getFrame() {
		return this;
	}

	/**
	 * @return This method create new object this class if variable player equal
	 *         null. Variable "player" is variable for this class.
	 */
	public static SingerGui getInstance() {
		if (player == null) {
			player = new SingerGui();
		}
		return player;
	}

	/**
	 * In this method- createGUI happen initialization of all variable for gui
	 */
	public void createGUI() {
		try {
			UIManager.setLookAndFeel(new McWinLookAndFeel());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (UnsupportedLookAndFeelException e) {
			logger.error("try set default lookAndFeel" + e.getStackTrace());
		}

		// menuBar:
		itemOpen = new JMenuItem("open");
		itemOpen.setName("iOpen");
		itemClear = new JMenuItem("clear");
		itemClear.setName("iClear");
		itemClose = new JMenuItem("close");
		itemClose.setName("iClose");
		menuFile = new JMenu("File");
		menuFile.add(itemOpen);
		menuFile.add(itemClear);
		menuFile.add(itemClose);

		itemAbout = new JMenuItem("about pro");
		itemAbout.setName("about");
		itemMenuSkins = new JMenu("skins:");
		rbtnSkinModern = new JRadioButton("modern");
		rbtnSkinModern.setName("modern");
		rbtnSkinClassic = new JRadioButton("classic", true);
		rbtnSkinClassic.setName("classic");
		btnGroup = new ButtonGroup();
		btnGroup.add(rbtnSkinModern);
		btnGroup.add(rbtnSkinClassic);
		itemMenuSkins.add(rbtnSkinModern);
		itemMenuSkins.add(rbtnSkinClassic);
		menuService = new JMenu("service");
		menuService.add(itemMenuSkins);
		menuService.add(itemAbout);
		topMenuBar = new JMenuBar();
		topMenuBar.add(menuFile);
		topMenuBar.add(menuService);

		//
		urlPlay = SingerGui.class.getResource("/images/play.png");
		urlPause = SingerGui.class.getResource("/images/pause.png");
		urlStop = SingerGui.class.getResource("/images/stop.png");
		urlPrev = SingerGui.class.getResource("/images/back.png");
		urlNext = SingerGui.class.getResource("/images/next.png");
		urlAdd = SingerGui.class.getResource("/images/open.png");
		urlVolume = SingerGui.class.getResource("/images/vol.png");

		btnPlay = new JButton(new ImageIcon(urlPlay));
		btnPlay.setName("play");
		btnPlay.setToolTipText("play");
		btnPlay.setBounds(8, 7, 30, 22);
		btnPause = new JButton(new ImageIcon(urlPause));
		btnPause.setName("pause");
		btnPause.setToolTipText("pause");
		btnPause.setBounds(40, 7, 30, 22);
		btnStop = new JButton(new ImageIcon(urlStop));
		btnStop.setName("stop");
		btnStop.setToolTipText("stop");
		btnStop.setBounds(72, 7, 30, 22);
		btnPrev = new JButton(new ImageIcon(urlPrev));
		btnPrev.setName("previous");
		btnPrev.setToolTipText("Previous");
		btnPrev.setBounds(109, 7, 30, 22);
		btnNext = new JButton(new ImageIcon(urlNext));
		btnNext.setName("next");
		btnNext.setToolTipText("next");
		btnNext.setBounds(141, 7, 30, 22);
		btnAdd = new JButton(new ImageIcon(urlAdd));
		btnAdd.setName("add");
		btnAdd.setToolTipText("add song");
		btnAdd.setBounds(20, 465, 35, 22);
		btnDel = new JButton("del");
		btnDel.setName("del");
		btnDel.setToolTipText("delete song");
		btnDel.setBounds(65, 465, 56, 22);
		togBtnVolume = new JToggleButton(new ImageIcon(urlVolume));
		togBtnVolume.setName("volume");
		togBtnVolume.setToolTipText("disable volume");
		togBtnVolume.setBounds(178, 7, 30, 22);

		// slider, label:
		slVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, 5);
		slVolume.setName("volume");
		slVolume.setToolTipText("volume");
		slVolume.setBounds(210, 15, 120, 15);
		lblVolume = new JLabel("5 %");
		lblVolume.setBounds(330, 15, 50, 22);
		slProgressSong = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
		slProgressSong.setName("progressSong");
		slProgressSong.setSnapToTicks(true);
		slProgressSong.setMinorTickSpacing(1);
		slProgressSong.setBounds(15, 40, 305, 15);
		lblTimeSong = new JLabel("...");
		lblTimeSong.setBounds(320, 30, 50, 22);
		lblNameSong = new JLabel("...");
		lblNameSong.setBounds(150, 465, 200, 25);

		// popupMenu:
		functionalPopMenu = new JPopupMenu();
		itemPopPlay = new JMenuItem("play");
		itemPopPlay.setName("ipPlay");
		itemPopPause = new JMenuItem("pause");
		itemPopPause.setName("ipPause");
		itemPopStop = new JMenuItem("stop");
		itemPopStop.setName("ipStop");
		itemPopAdd = new JMenuItem("add");
		itemPopAdd.setName("ipAdd");
		itemPopDel = new JMenuItem("del");
		itemPopDel.setName("ipDel");
		functionalPopMenu.add(itemPopPlay);
		functionalPopMenu.add(itemPopPause);
		functionalPopMenu.add(itemPopStop);
		functionalPopMenu.add(itemPopAdd);
		functionalPopMenu.add(itemPopDel);

		// JList(playList):
		playList = new JList<String>();
		playList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playList.setBounds(5, 55, 364, 405);
		playList.setComponentPopupMenu(functionalPopMenu);
		scrPanelList = new JScrollPane(playList);
		scrPanelList.setBounds(5, 55, 364, 405);

		panel = new JPanel();
		panel.setName("panel");
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
		panel.setLayout(null);
		panel.add(btnPlay);
		panel.add(btnPause);
		panel.add(btnStop);
		panel.add(btnPrev);
		panel.add(btnNext);
		panel.add(btnAdd);
		panel.add(btnDel);
		panel.add(togBtnVolume);
		//
		panel.add(slVolume);
		panel.add(slProgressSong);
		panel.add(scrPanelList);
		panel.add(slVolume);
		panel.add(lblVolume);
		panel.add(slProgressSong);
		panel.add(lblTimeSong);
		panel.add(lblNameSong);

		//

		this.setTitle("LightPlayer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(380, 550);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setJMenuBar(topMenuBar);
		this.getContentPane().add(panel);
		this.setVisible(true);
	}

	/**
	 * In this method setting listeners for all buttons and other components on
	 * the gui player:
	 * 
	 */
	public void addButtonListeners() {
		SingerListener singerListener = new SingerListener();
		btnPlay.addActionListener(singerListener);
		btnPause.addActionListener(singerListener);
		btnStop.addActionListener(singerListener);
		btnPrev.addActionListener(singerListener);
		btnNext.addActionListener(singerListener);
		btnAdd.addActionListener(singerListener);
		btnDel.addActionListener(singerListener);
		togBtnVolume.addActionListener(singerListener);
		//
		itemOpen.addActionListener(singerListener);
		itemClear.addActionListener(singerListener);
		itemClose.addActionListener(singerListener);
		itemAbout.addActionListener(singerListener);

		itemPopPlay.addActionListener(singerListener);
		itemPopPause.addActionListener(singerListener);
		itemPopStop.addActionListener(singerListener);
		itemPopAdd.addActionListener(singerListener);
		itemPopDel.addActionListener(singerListener);

		rbtnSkinClassic.addActionListener(singerListener);
		rbtnSkinModern.addActionListener(singerListener);

		slVolume.addChangeListener(singerListener);
		slProgressSong.addChangeListener(singerListener);

		playList.addMouseListener(singerListener);
		playList.addKeyListener(singerListener);

	}

	public JList<String> getPlayList() {
		return playList;
	}

	public JLabel getNameCurrentSound() {
		return lblNameSong;
	}

	public JSlider getVolumeSlider() {
		return slVolume;
	}

	public void setVolumeSlider(JSlider slider) {
		slVolume = slider;

	}

	public JSlider getSoundProgress() {
		return slProgressSong;
	}

	public JLabel getVolumeLabel() {
		return lblVolume;
	}

	public JLabel getSoundProgressLabel() {
		return lblTimeSong;
	}

}
