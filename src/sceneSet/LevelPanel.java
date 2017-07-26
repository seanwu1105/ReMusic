package sceneSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import audio.ButtonSoundFX;
import buttonStyle.GamingButtonStyle;
import buttonStyle.LevelSelButtonStyle;
import controller.LevelController;
import controller.SceneController;
import initialization.DefaultSetting;

@SuppressWarnings("serial")
public class LevelPanel extends JPanel
{
	private JButton[] levelBtn;

	public LevelPanel()
	{
		setLayout(new BorderLayout());
		setBackground(DefaultSetting.bgColor);
		setBorder(new EmptyBorder(0, 0, 25, 0));
		setPreferredSize(DefaultSetting.size);

		/* HEADER */
		final JPanel header = new JPanel(new BorderLayout());
		final JPanel headerLeft = new JPanel(new BorderLayout());
		final JLabel title = new JLabel("Level Selection");
		final JButton menuBtn = new JButton("");
		title.setFont(DefaultSetting.h2);
		title.setOpaque(true);
		title.setBackground(DefaultSetting.swatches1);
		title.setForeground(Color.WHITE);
		title.setBorder(new EmptyBorder(10, 10, 10, 10));
		menuBtn.setName("menuBtn");
		menuBtn.addChangeListener(new ButtonSoundFX());
		menuBtn.setBackground(DefaultSetting.swatches4);
		menuBtn.setForeground(Color.white);
		menuBtn.setUI(new GamingButtonStyle()); // customize the button.
		menuBtn.addActionListener(new SceneController());
		menuBtn.setActionCommand("Level -> Menu");
		menuBtn.setIcon(new ImageIcon("img/menuIcon.png"));
		menuBtn.setHorizontalAlignment(SwingConstants.CENTER);
		headerLeft.setBackground(DefaultSetting.bgColor);
		headerLeft.add(menuBtn, BorderLayout.EAST);
		headerLeft.add(title, BorderLayout.WEST);
		header.add(headerLeft, BorderLayout.WEST);
		header.setOpaque(false);
		add(header, BorderLayout.NORTH);

		/* CONTENTS */
		JPanel levelBtnSet = new JPanel(new GridLayout(3, 0, 10, 10));
		levelBtn = new JButton[LevelController.totalLevel];
		for (int i = 0; i < levelBtn.length; i++)
		{
			levelBtn[i] = new JButton(String.valueOf(i + 1));
			levelBtn[i].setActionCommand("Sel -> level " + (i + 1));
			levelBtn[i].setFont(DefaultSetting.h1);
			levelBtn[i].setBackground(DefaultSetting.swatches2);
			levelBtn[i].setForeground(Color.WHITE);
			levelBtn[i].setUI(new LevelSelButtonStyle());
			levelBtn[i].addActionListener(new SceneController());
			levelBtn[i].addChangeListener(new ButtonSoundFX());
			levelBtn[i].setEnabled(false);
			levelBtnSet.add(levelBtn[i]);
		}
		levelBtnSet.setBorder(new EmptyBorder(30, 30, 30, 30));
		levelBtnSet.setOpaque(false);

		refreshCompletedLevel();

		add(levelBtnSet, BorderLayout.CENTER);
	}

	public void refreshCompletedLevel()
	{
		for (int i = 0; i < LevelController.totalLevel; i++)
			levelBtn[i].setEnabled(false);
		for (int i = 0; i < LevelController.completedLevel; i++)
			levelBtn[i].setEnabled(true);
	}
}
