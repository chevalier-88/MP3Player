package handle;

import gui.SingerGui;

import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import org.apache.log4j.Logger;

public class SingerController {
	private static final Logger logger = Logger
			.getLogger(SingerController.class);
	private File currentSoundFile;
	private DefaultListModel<String> soundModelCatalog = new DefaultListModel<String>();;
	private ArrayList<File> CatalogSounds = new ArrayList<File>();
	private SingerGui guiSinger;
	private BasicPlayer player = new BasicPlayer();
	private int indexCurrentSound;

	public SingerController(SingerListener listener) {
		guiSinger = SingerGui.getInstance();
		player.addBasicPlayerListener(listener);
	}

	public void playSound() {
		if (CatalogSounds.size() > 0) {
			if (player.getStatus() == BasicPlayer.PAUSED) {
				try {
					player.resume();
				} catch (BasicPlayerException bpe) {
					logger.error("player try resume move " + currentSoundFile
							+ ". " + bpe.getStackTrace());
				}
				return;
			}
			if (guiSinger.getPlayList().isSelectionEmpty()) {
				indexCurrentSound = 0;
				guiSinger.getPlayList().setSelectedIndex(indexCurrentSound);
				currentSoundFile = CatalogSounds.get(indexCurrentSound);
			} else {
				indexCurrentSound = guiSinger.getPlayList().getSelectedIndex();
				currentSoundFile = CatalogSounds.get(indexCurrentSound);
			}
			try {
				player.open(currentSoundFile);
				player.play();
				guiSinger.getNameCurrentSound().setText(
						currentSoundFile.getName());
			} catch (BasicPlayerException bpe) {
				logger.error("player try move selected " + currentSoundFile
						+ ". " + bpe.getStackTrace());
			}
		}
	}

	public void pauseSound() {
		try {
			player.pause();
		} catch (BasicPlayerException bpe) {
			logger.error("player try set up " + currentSoundFile + " pause. "
					+ bpe.getStackTrace());
		}
	}

	public void stopSound() {
		try {
			player.stop();
		} catch (BasicPlayerException bpe) {
			logger.error("player try set up " + currentSoundFile + " stop. "
					+ bpe.getStackTrace());
		}
	}

	public ArrayList<File> addSound() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "Files *.mp3";
			}

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getAbsolutePath().endsWith("mp3");
			}
		});

		if (CatalogSounds.size() <= 0) {
			int resultAdding = fileChooser.showOpenDialog(null);
			if (resultAdding == JFileChooser.APPROVE_OPTION) {
				for (int i = 0; i < fileChooser.getSelectedFiles().length; i++) {
					currentSoundFile = fileChooser.getSelectedFiles()[i];
					soundModelCatalog.addElement(currentSoundFile.getName()
							.substring(0,
									(currentSoundFile.getName().length() - 4)));
					CatalogSounds.add(currentSoundFile);
				}
				guiSinger.getPlayList().setModel(soundModelCatalog);
				indexCurrentSound = 0;
				guiSinger.getPlayList().setSelectedIndex(indexCurrentSound);
			}
		} else {
			int resFill = fileChooser.showOpenDialog(null);
			if (resFill == JFileChooser.APPROVE_OPTION) {
				for (int i = 0; i < fileChooser.getSelectedFiles().length; i++) {
					currentSoundFile = fileChooser.getSelectedFiles()[i];
					soundModelCatalog.addElement(currentSoundFile.getName()
							.substring(0,
									(currentSoundFile.getName().length() - 4)));
					CatalogSounds.add(currentSoundFile);
				}
			}
		}
		return CatalogSounds;
	}

	public void deleteSound() {
		if ((CatalogSounds.size() != 0)
				&& (!guiSinger.getPlayList().isSelectionEmpty())) {
			indexCurrentSound = guiSinger.getPlayList().getSelectedIndex();
			soundModelCatalog.removeElementAt(indexCurrentSound);
			CatalogSounds.remove(indexCurrentSound);
			guiSinger.getPlayList().setModel(soundModelCatalog);
			guiSinger.getPlayList().setSelectedIndex(indexCurrentSound);
		}
	}

	public boolean nextSound(double d) {
		if (indexCurrentSound < CatalogSounds.size()) {
			guiSinger.getPlayList().setSelectedIndex(++indexCurrentSound);
			this.playSound();
			try {
				player.setGain(d);
			} catch (BasicPlayerException e) {
				logger.error("player try move next " + currentSoundFile	+ " sound. "+e.getStackTrace());
			}
			return true;
		}
		return false;
	}

	public boolean previousSound(double d) {
		if (indexCurrentSound > 0) {
			guiSinger.getPlayList().setSelectedIndex(--indexCurrentSound);
			this.playSound();
			try {
				player.setGain(d);
			} catch (BasicPlayerException e) {
				logger.error("player try move previous " + currentSoundFile	+ " sound. "+e.getStackTrace());
			}
			return true;
		}
		return false;
	}

	public void setVolume(double d) {
		if (CatalogSounds.size() > 0) {
			try {
				player.setGain(d);
			} catch (BasicPlayerException bpe) {
				logger.error("try set up voulume for "+currentSoundFile+". " + bpe.getStackTrace());
			}
		}
	}

	public void doCleanPlaylist() {
		try {
			player.stop();
			CatalogSounds.removeAll(CatalogSounds);
			soundModelCatalog.removeAllElements();
			guiSinger.getPlayList().setModel(soundModelCatalog);
		} catch (BasicPlayerException e) {
			logger.error("player try cleaning catalog sound file. " + e.getStackTrace());
		}
	}

	public void jumpBySound(long d, double vol) {
		try {
			player.seek(d);
			player.setGain(vol);
		} catch (BasicPlayerException e) {
			logger.error("player try jump by sound "+ currentSoundFile+". "+ e.getStackTrace());
		}
	}
}
