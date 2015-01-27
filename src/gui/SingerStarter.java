package gui;


public class SingerStarter {
	public static void main(String[] args) {
		SingerGui singer = SingerGui.getInstance();
		singer.createGUI();
		singer.addButtonListeners();
	}
}
