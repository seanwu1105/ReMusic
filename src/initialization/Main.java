package initialization;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import audio.FlickerBG;
import controller.LevelController;
import sceneSet.LevelPanel;
import sceneSet.MenuPanel;
import sceneSet.SettingAboutPanel;
import sceneSet.TutPanel;
import sceneSet.gamingContents.GamingPanel;

public class Main
{
	final public static JFrame f = new JFrame("ReMusic");
	public static MenuPanel menuPanel;
	public static GamingPanel gamingPanel;
	public static LevelPanel levelPanel;
	public static TutPanel tutPanel;
	public static SettingAboutPanel settingPanel;

	public static void main(String[] args)
	{
		DefaultSetting.registerCustomFont();
		LevelController.readRecord();

		menuPanel = new MenuPanel();
		gamingPanel = new GamingPanel();
		levelPanel = new LevelPanel();
		tutPanel = new TutPanel();
		settingPanel = new SettingAboutPanel();

		final Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocation((int) ((scrSize.getWidth() - DefaultSetting.size.width) / 2),
				(int) ((scrSize.getHeight() - DefaultSetting.size.height) / 2));
		f.setVisible(true);
		f.setResizable(false);
		f.add(menuPanel);
		FlickerBG.bgmEvent(true, menuPanel, "MenuBGM");
		f.pack();
	}
}
