package audio;

import java.awt.Color;

import javax.swing.JPanel;

import initialization.DefaultSetting;

public class FlickerBG
{
	/* BGM */
	private static Thread flicker;
	private static boolean flickerRunFlag;
	private static AudioPlayer bgm;

	public static void bgmEvent(boolean isPlay, JPanel panel, String bgmName)
	{
		if (isPlay)
		{
			bgm = new AudioPlayer("sound/" + bgmName + ".wav");
			flickerRunFlag = true; // Mix flag and .join() to achieve .stop().
			flicker = new Thread() // Handle the decorative blocks animation.
			{
				public void run()
				{
					int bgmBeatTime = 0;
					if (bgmName.startsWith("Menu"))
						bgmBeatTime = DefaultSetting.menuBgmBeatTime;
					if (bgmName.startsWith("Level"))
						bgmBeatTime = DefaultSetting.levelBgmBeatTime;
					if (bgmName.startsWith("Tut"))
						bgmBeatTime = DefaultSetting.tutBgmBeatTime;
					int i = 0;
					while (flickerRunFlag)
					{
						if (i == 0)
							panel.setBackground(new Color(0XDBD9B3));
						else
							panel.setBackground(DefaultSetting.bgColor);
						if (i >= 3)
							i = 0;
						else
							i++;
						try
						{
							sleep(bgmBeatTime);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
			};
			flicker.start();
			bgm.musicLoop();
		}
		else // Stop the Menu decorative animation.
		{
			flickerRunFlag = false;
			try
			{
				flicker.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			bgm.musicStop();
		}
	}
}
