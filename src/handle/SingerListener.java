package handle;

import gui.SingerGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import org.apache.log4j.Logger;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

public class SingerListener extends MouseAdapter implements ActionListener,
		ChangeListener, BasicPlayerListener, KeyListener {

	private static final Logger logger = Logger.getLogger(SingerListener.class);

	private double levelVolume = 0.05;
	private long secondsAmount;
	private long duration;
	private int bytesLen;
	private double posValue = 0.0;
	private boolean movingFromJump = false;
	private boolean moveAutomatic = false;

	SingerController playController = new SingerController(this);
	SingerGui viewPlayer = SingerGui.getInstance();
	JSlider slVolume = viewPlayer.getVolumeSlider();
	JSlider progressSound = viewPlayer.getSoundProgress();

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			playController.playSound();
			playController.setVolume(levelVolume);
		}
	}

	public void opened(Object arg0, Map map) {
		duration = (long) Math
				.round((((Long) map.get("duration")).longValue()) / 1000000);
		bytesLen = (int) Math.round(((Integer) map.get("mp3.length.bytes"))
				.intValue());
	}

	public void stateUpdated(BasicPlayerEvent bpe) {
		int state = bpe.getCode();
		if (state == BasicPlayerEvent.PLAYING) {
			movingFromJump = false;
		} else if (state == BasicPlayerEvent.SEEKING) {
			movingFromJump = true;
		} else if (state == BasicPlayerEvent.EOM) {
			playController.nextSound(levelVolume);
		}
	}

	public void stateChanged(ChangeEvent e) {
		JSlider sliderComponent = (JSlider) e.getSource();
		if (sliderComponent.getName().equals("volume")) {
			int i = slVolume.getValue();
			levelVolume = (double) slVolume.getValue() / 100;
			playController.setVolume(levelVolume);
			viewPlayer.getVolumeLabel().setText(i + "%");
		} else if (sliderComponent.getName().equals("progressSong")) {
			if (viewPlayer.getPlayList().getModel() == null
					|| viewPlayer.getPlayList().getModel().getSize() < 1) {
				sliderComponent.setEnabled(false);
			} else {
				sliderComponent.setEnabled(true);
				if (!progressSound.getValueIsAdjusting()) {
					if (moveAutomatic == true) {
						moveAutomatic = false;
						posValue = progressSound.getValue() * 1.0 / 1000;
						processSeek(posValue);
					}
				} else {
					moveAutomatic = true;
					movingFromJump = true;
				}
			}
		}
	}

	private void processSeek(double bytes) {
		try {
			long skipBytes = (long) Math.round(((Integer) bytesLen).intValue()
					* bytes);
			playController.jumpBySound(skipBytes, levelVolume);
		} catch (Exception e) {
			logger.error("try jump by the sound. " + e.getStackTrace());
		}
	}

	public void actionPerformed(ActionEvent e) {
		AbstractButton button = (AbstractButton) e.getSource();
		String nameButton = button.getName();
		switch (nameButton) {
		case "play":
			playController.playSound();
			playController.setVolume(levelVolume);
			break;
		case "ipPlay":
			playController.playSound();
			playController.setVolume(levelVolume);
			break;
		case "pause":
			playController.pauseSound();
			break;
		case "ipPause":
			playController.pauseSound();
			break;
		case "stop":
			playController.stopSound();
			break;
		case "ipStop":
			playController.stopSound();
			break;
		case "previous":
			playController.previousSound(levelVolume);
			break;
		case "next":
			playController.nextSound(levelVolume);
			break;
		case "add":
			playController.addSound();
			break;
		case "ipAdd":
			playController.addSound();
			break;
		case "del":
			playController.deleteSound();
			break;
		case "ipDel":
			playController.deleteSound();
			break;
		case "iOpen":
			playController.addSound();
			break;
		case "iClear":
			playController.doCleanPlaylist();
			break;
		case "iClose":
			System.exit(0);
			break;
		case "about":
			JOptionPane.showMessageDialog(null,
					"This is simple player for move mp3 files. \n"
							+ "Version 1.2.0");
			break;
		case "modern":
			try {
				UIManager.setLookAndFeel(new NimbusLookAndFeel());
				SwingUtilities.updateComponentTreeUI(viewPlayer.getFrame());
			} catch (UnsupportedLookAndFeelException e1) {
				logger.error("set skin - modern. " + e1.getStackTrace());
			}
			break;
		case "classic":
			try {
				UIManager.setLookAndFeel(new McWinLookAndFeel());
				SwingUtilities.updateComponentTreeUI(viewPlayer.getFrame());
			} catch (UnsupportedLookAndFeelException e1) {
				logger.error("set skin - classic. " + e1.getStackTrace());
			}
			break;
		case "volume":
			JToggleButton tgBtn = (JToggleButton) button;
			URL urlVolume = SingerGui.class.getResource("/images/vol.png");
			URL urlVolumeDis = SingerGui.class
					.getResource("/images/volDis.png");
			if (tgBtn.isSelected()) {
				System.out.println("button is selected");
				tgBtn.setIcon(new ImageIcon(urlVolumeDis));
				playController.setVolume(0);
			} else {
				tgBtn.setIcon(new ImageIcon(urlVolume));
				System.out.println("button is unselected");
				playController.setVolume(levelVolume);
			}
			break;

		default:
			throw new UnsupportedOperationException(
					"Not identefication operation in using press button");

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if ((e.getButton() == 1) && (e.getClickCount() == 2)) {
			playController.playSound();
			playController.setVolume(levelVolume);
		}
	}

	@Override
	public void progress(int bytesread, long arg1, byte[] arg2, Map arg3) {
		float progress = -1.0f;
		if ((bytesread > 0) && ((duration > 0))) {
			progress = bytesread * 1.0f / bytesLen * 1.0f;
		}
		secondsAmount = (long) (duration * progress);
		int minuts = (int) secondsAmount / 60;
		int seconds = (int) secondsAmount % 60;
		viewPlayer.getSoundProgressLabel().setText(minuts + ":" + seconds);
		if (duration != 0) {
			if (movingFromJump == false) {
				progressSound.setValue(((int) Math.round(secondsAmount * 1000
						/ duration)));
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		// NOOP
		// throw new UnsupportedOperationException();
	}

	public void setController(BasicController arg0) {
		// NOOP
		// throw new UnsupportedOperationException();
	}

	public void keyReleased(KeyEvent e) {
		// NOOP
		// throw new UnsupportedOperationException();
	}

}
