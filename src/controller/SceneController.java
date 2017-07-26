package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import audio.FlickerBG;
import initialization.Main;

public class SceneController implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String actionCmd = e.getActionCommand();

		/** Menu Button Set **/
		if (actionCmd.startsWith("-> "))
		{
			FlickerBG.bgmEvent(false, Main.menuPanel, "MenuBGM");
			if (actionCmd.equals("-> Gaming"))
			{
				LevelController.currentLevel = 1;
				Main.f.remove(Main.menuPanel);
				Main.gamingPanel.refreshLevelContents();
				Main.f.add(Main.gamingPanel);
			}
			if (actionCmd.equals("-> Level"))
			{
				FlickerBG.bgmEvent(true, Main.levelPanel, "LevelBGM");
				Main.f.remove(Main.menuPanel);
				Main.f.add(Main.levelPanel);
			}
			if (actionCmd.equals("-> Tut"))
			{
				FlickerBG.bgmEvent(true, Main.tutPanel, "TutBGM");
				Main.tutPanel.flickerBtn(true);
				Main.f.remove(Main.menuPanel);
				Main.f.add(Main.tutPanel);
			}
			if (actionCmd.equals("-> Setting"))
			{
				Main.settingPanel.refreshSettingAboutNum();
				Main.settingPanel.LogoSpeak();
				Main.f.remove(Main.menuPanel);
				Main.f.add(Main.settingPanel);
			}
			if (actionCmd.equals("-> exit"))
			{
				Main.f.dispose();
			}
		}

		/** Back to Menu Button Set **/
		if (actionCmd.endsWith(" -> Menu"))
		{
			if (actionCmd.equals("Gaming -> Menu"))
			{
				Main.f.remove(Main.gamingPanel);
				Main.f.add(Main.menuPanel);
			}
			if (actionCmd.equals("Level -> Menu"))
			{
				FlickerBG.bgmEvent(false, Main.levelPanel, "LevelBGM");
				Main.f.remove(Main.levelPanel);
				Main.f.add(Main.menuPanel);
			}
			if (actionCmd.equals("Tut -> Menu"))
			{
				FlickerBG.bgmEvent(false, Main.tutPanel, "TutBGM");
				Main.tutPanel.flickerBtn(false);
				Main.f.remove(Main.tutPanel);
				Main.f.add(Main.menuPanel);
			}
			if (actionCmd.equals("Setting -> Menu"))
			{
				Main.f.remove(Main.settingPanel);
				Main.f.add(Main.menuPanel);
			}
			FlickerBG.bgmEvent(true, Main.menuPanel, "MenuBGM");
		}

		/** Level Selection **/
		if (actionCmd.startsWith("Sel -> level "))
		{
			FlickerBG.bgmEvent(false, Main.levelPanel, "LevelBGM");
			for (int i = 1; i <= LevelController.totalLevel; i++)
			{
				if (actionCmd.endsWith(String.valueOf(i)))
				{
					LevelController.currentLevel = i;
					Main.f.remove(Main.levelPanel);
					Main.gamingPanel.refreshLevelContents();
					Main.f.add(Main.gamingPanel);
				}
			}
		}

		/** Complete Whole Game **/
		if (actionCmd.equals("nextLevel") && LevelController.currentLevel > LevelController.totalLevel)
		// Since the priority of ActionListeners is "this > SceneController" of
		// nextLevelBtn in GamingPanel.java, the currentLevel will plus one
		// before here has been triggered.
		{
			LevelController.currentLevel = LevelController.totalLevel;
			LevelController.gameCompleted = true;
			LevelController.writeRecord();
			FlickerBG.bgmEvent(true, Main.menuPanel, "MenuBGM");
			Main.gamingPanel.stopRhythmThread();
			Main.gamingPanel.stopSampleThread();
			Main.menuPanel.gameCompletedRefresh();
			Main.f.remove(Main.gamingPanel);
			Main.f.add(Main.menuPanel);
		}

		Main.f.revalidate();
		Main.f.repaint();
		Main.f.pack();
	}
}
