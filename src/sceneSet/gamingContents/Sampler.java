package sceneSet.gamingContents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import audio.AudioPlayer;
import buttonStyle.GamingButtonStyle;
import buttonStyle.PatternButtonStyle;
import initialization.DefaultSetting;

@SuppressWarnings("serial")
public class Sampler extends JPanel implements ActionListener
{
	private final int semiquaver = DefaultSetting.semiquaver;
	public String samplerName;
	public final JToggleButton[] pattern = new JToggleButton[semiquaver];

	Sampler(String sourceName)
	{
		this.samplerName = sourceName;
		setOpaque(false);
		setLayout(new BorderLayout());
		setPreferredSize(DefaultSetting.samplerSize);

		final JButton soundSource = new JButton(sourceName);
		soundSource.setPreferredSize(DefaultSetting.samplerSoundSourceSize);
		soundSource.setBackground(DefaultSetting.swatches4);
		soundSource.setForeground(Color.WHITE);
		soundSource.setFont(DefaultSetting.h3);
		soundSource.setUI(new GamingButtonStyle());
		soundSource.addActionListener(this);
		soundSource.setActionCommand("soundSource");
		soundSource.setName(sourceName + " Sampler.");
		add(soundSource, BorderLayout.WEST);
		final JPanel patternLayer = new JPanel(new GridLayout(1, semiquaver));
		patternLayer.setOpaque(false);
		for (int i = 0; i < semiquaver; i++)
		{
			pattern[i] = new JToggleButton();
			pattern[i].setUI(new PatternButtonStyle());
			pattern[i].setBackground(DefaultSetting.swatches1);
			pattern[i].setForeground(DefaultSetting.swatches2);
			patternLayer.add(pattern[i]);
		}
		add(patternLayer, BorderLayout.CENTER);
	}

	@Override // Handle the soundSource preview.
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("soundSource"))
		{
			AudioPlayer source = new AudioPlayer("sound/packs/" + samplerName + ".wav");
			source.musicPlay();
		}
	}
}
