package sceneSet.gamingContents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import controller.LevelController;
import initialization.DefaultSetting;

@SuppressWarnings("serial")
public class ChannelRack extends JPanel
{
	private static int channelNumber;
	public static Sampler[] samplerSet;

	ChannelRack()
	{
		final int channelPositionHeightAdjust = 150; // I hate the JFrame size
														// doesn't match the
														// size of contents.
		channelNumber = LevelController.channelNumber[LevelController.currentLevel - 1];
		samplerSet = new Sampler[channelNumber];
		setBackground(DefaultSetting.bgColor);
		final JPanel channelRackTopSpace = new JPanel();
		final JPanel channelRackBottomSpace = new JPanel();
		channelRackTopSpace.setOpaque(false);
		channelRackTopSpace.setPreferredSize(new Dimension(DefaultSetting.size.width, (DefaultSetting.size.height
				- channelNumber * DefaultSetting.samplerSize.height - channelPositionHeightAdjust) / 2));
		channelRackBottomSpace.setOpaque(false);
		channelRackBottomSpace.setPreferredSize(new Dimension(DefaultSetting.size.width, (DefaultSetting.size.height
				- channelNumber * DefaultSetting.samplerSize.height - channelPositionHeightAdjust) / 2));
		add(channelRackTopSpace, BorderLayout.NORTH);

		final JPanel channelRackContents = new JPanel(new GridLayout(channelNumber, 1, 0, 5));
		channelRackContents.setOpaque(false);
		add(channelRackContents, BorderLayout.CENTER);
		for (int i = 0; i < channelNumber; i++)
		{
			samplerSet[i] = new Sampler(LevelController.soundPackID[LevelController.currentLevel - 1][i]);
			channelRackContents.add(samplerSet[i]);
		}
		add(channelRackBottomSpace, BorderLayout.SOUTH);
	}
}
