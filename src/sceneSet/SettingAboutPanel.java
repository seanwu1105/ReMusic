package sceneSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import audio.AudioPlayer;
import audio.ButtonSoundFX;
import buttonStyle.GamingButtonStyle;
import buttonStyle.SettingButtonStyle;
import controller.LevelController;
import controller.SceneController;
import initialization.DefaultSetting;
import initialization.Main;

@SuppressWarnings("serial")
public class SettingAboutPanel extends JPanel implements ActionListener
{
	/** Status bar variables and objects **/
	public final static JTextField statusBar = new JTextField();
	// private static int

	private final JButton completeLevelBtn;
	private final JButton totalRetryBtn;
	private final JLabel completeLevelNum = new JLabel();
	private final JLabel totalRetryNum = new JLabel();

	private static int completeLevelBtnStatus = 0, totalRetryBtnStatus = 0, menuBtnStatus = 0;

	public SettingAboutPanel()
	{
		setLayout(new BorderLayout());
		setBackground(DefaultSetting.bgColor);
		setBorder(new EmptyBorder(0, 0, 25, 0));
		setPreferredSize(DefaultSetting.size);

		/* HEADER */
		final JPanel header = new JPanel(new BorderLayout());
		final JPanel headerLeft = new JPanel(new BorderLayout());
		final JLabel title = new JLabel("Setting & About");
		final JButton menuBtn = new JButton("");
		header.setBackground(DefaultSetting.swatches3);
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
		menuBtn.addActionListener(this);
		menuBtn.setActionCommand("Setting -> Menu");
		menuBtn.setIcon(new ImageIcon("img/menuIcon.png"));
		menuBtn.setHorizontalAlignment(SwingConstants.CENTER);
		headerLeft.setBackground(DefaultSetting.bgColor);
		headerLeft.add(menuBtn, BorderLayout.EAST);
		headerLeft.add(title, BorderLayout.WEST);
		final JPanel headerRight = new JPanel(new BorderLayout());
		headerRight.setBackground(header.getBackground());
		header.add(headerRight, BorderLayout.CENTER);
		statusBar.setHorizontalAlignment(SwingConstants.RIGHT);
		statusBar.setEditable(false);
		statusBar.setOpaque(true);
		statusBar.setFont(DefaultSetting.f2);
		statusBar.setBackground(header.getBackground().brighter());
		statusBar.setBorder(new LineBorder(header.getBackground(), 4));
		headerRight.add(statusBar, BorderLayout.CENTER);
		header.add(headerLeft, BorderLayout.WEST);
		header.setOpaque(false);
		add(header, BorderLayout.NORTH);

		/* CONTENTS */
		final JPanel content = new JPanel(new BorderLayout(10, 10));
		final JPanel settingSet = new JPanel(new GridLayout(1, 2, 10, 0));
		final JPanel completeLevelSet = new JPanel(new GridLayout(1, 3));
		final JLabel completeLevelText = new JLabel("<html><center>Completed<br>Level</center></html>");
		completeLevelBtn = new JButton("<html><center>CLEAR<br>RECORD</center></html>");
		final JPanel totalRetrySet = new JPanel(new GridLayout(1, 3));
		final JLabel totalRetryText = new JLabel("<html><center>Total<br>Retry Times</center></html>");
		totalRetryBtn = new JButton("<html><center>CLEAR<br>RECORD</center></html>");
		final JPanel about = new JPanel(new BorderLayout());
		final JPanel aboutHeader = new JPanel(new BorderLayout());
		final JLabel smallLogo = new JLabel();
		final JLabel subheader = new JLabel();
		final JTextArea aboutText = new JTextArea();
		add(content, BorderLayout.CENTER);
		content.setBorder(new EmptyBorder(20, 20, 0, 20));
		content.setOpaque(false);
		content.add(settingSet, BorderLayout.NORTH);
		content.add(about, BorderLayout.CENTER);
		settingSet.add(completeLevelSet);
		settingSet.add(totalRetrySet);
		settingSet.setOpaque(false);
		completeLevelSet.setOpaque(false);
		completeLevelSet.add(completeLevelText);
		completeLevelSet.add(completeLevelNum);
		completeLevelSet.add(completeLevelBtn);
		completeLevelText.setHorizontalAlignment(SwingConstants.CENTER);
		completeLevelText.setBackground(DefaultSetting.swatches3);
		completeLevelText.setOpaque(true);
		completeLevelText.setBorder(new EmptyBorder(15, 0, 15, 0));
		completeLevelText.setFont(DefaultSetting.f1);
		completeLevelText.setForeground(Color.WHITE);
		completeLevelNum.setHorizontalAlignment(SwingConstants.CENTER);
		completeLevelNum.setFont(DefaultSetting.h1);
		completeLevelNum.setBackground(Color.WHITE);
		completeLevelNum.setOpaque(true);
		completeLevelNum.setBorder(new LineBorder(completeLevelText.getBackground(), 6));
		completeLevelBtn.addChangeListener(new ButtonSoundFX());
		completeLevelBtn.setUI(new SettingButtonStyle());
		completeLevelBtn.setBackground(DefaultSetting.swatches1);
		completeLevelBtn.setForeground(DefaultSetting.swatches2.brighter());
		completeLevelBtn.setFont(DefaultSetting.f1);
		completeLevelBtn.setActionCommand("clearLevel");
		completeLevelBtn.addActionListener(this);
		completeLevelBtn.setName("completeLevelBtn");
		totalRetrySet.setOpaque(false);
		totalRetrySet.add(totalRetryText);
		totalRetrySet.add(totalRetryNum);
		totalRetrySet.add(totalRetryBtn);
		totalRetryText.setHorizontalAlignment(SwingConstants.CENTER);
		totalRetryText.setBackground(DefaultSetting.swatches3);
		totalRetryText.setOpaque(true);
		totalRetryText.setBorder(new EmptyBorder(15, 0, 15, 0));
		totalRetryText.setFont(DefaultSetting.f1);
		totalRetryText.setForeground(Color.WHITE);
		totalRetryNum.setHorizontalAlignment(SwingConstants.CENTER);
		totalRetryNum.setFont(DefaultSetting.h1);
		totalRetryNum.setBackground(Color.WHITE);
		totalRetryNum.setOpaque(true);
		totalRetryNum.setBorder(new LineBorder(totalRetryText.getBackground(), 6));
		totalRetryBtn.addChangeListener(new ButtonSoundFX());
		totalRetryBtn.setUI(new SettingButtonStyle());
		totalRetryBtn.setBackground(DefaultSetting.swatches1);
		totalRetryBtn.setForeground(DefaultSetting.swatches2.brighter());
		totalRetryBtn.setFont(DefaultSetting.f1);
		totalRetryBtn.setActionCommand("clearRetry");
		totalRetryBtn.addActionListener(this);
		totalRetryBtn.setName("totalRetryBtn");
		about.setBackground(DefaultSetting.bgColor.brighter());
		about.setBorder(new EmptyBorder(20, 20, 20, 20));
		about.add(aboutHeader, BorderLayout.NORTH);
		about.add(aboutText, BorderLayout.CENTER);
		aboutHeader.add(smallLogo, BorderLayout.WEST);
		aboutHeader.add(subheader, BorderLayout.CENTER);
		aboutHeader.setOpaque(false);
		smallLogo.setIcon(new ImageIcon("img/smallLogo.png"));
		subheader.setBorder(new EmptyBorder(0, 15, 0, 15));
		subheader.setText(
				"<html>ReMusic v2.0 [completed/release] 2016.5<br>JRE 1.8.0_91<br><b>Programming & Graphics- Sean Wu (GLaDOS1105)<html>");
		aboutText.setEditable(false);
		aboutText.setWrapStyleWord(true);
		aboutText.setLineWrap(true);
		aboutText.setMargin(new Insets(25, 0, 0, 0));
		aboutText.setOpaque(false);
		aboutText.setText("Copyright (C) 2016 Sean Wu (GLaDOS1105)\n"
				+ "Button Style Design Source Code: http://stackoverflow.com/a/23699047\n"
				+ "Audio Player Source Code: https://youtu.be/QVrxiJyLTqU\n"
				+ "BPM Detect and Sound Effect Source: FL Studio v12.2\n"
				+ "Images Powered by Adobe Photoshop CC 2016.\n" + "Color Swatches by Adobe Color CC (Kuler).\n"
				+ "Thanks for ZHUANG, B.R. to help me overcome the critical errors of scene switch controller.\n"
				+ "...and thanks for all the betatesters... you!\n\n"
				+ "This is the final Project of Introduction to Computer Science ¢ºLab, Computer Science and Information Engineering, National Central University.\n"
				+ "seanwu1105@gmail.com\t104502551.cc.ncu.edu.tw\n");

		refreshSettingAboutNum();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().startsWith("clear"))
		{
			if (e.getActionCommand().endsWith("Level"))
			{
				LevelController.completedLevel = 1;
				Main.levelPanel.refreshCompletedLevel();
			}
			if (e.getActionCommand().endsWith("Retry"))
			{
				LevelController.totalRetryTimes = 0;
			}
			refreshSettingAboutNum();
		}
	}

	public void refreshSettingAboutNum()
	{
		completeLevelNum.setText(String.valueOf(LevelController.completedLevel));
		totalRetryNum.setText(String.valueOf(LevelController.totalRetryTimes));
		if (LevelController.completedLevel == 1)
			completeLevelBtn.setEnabled(false);
		else
			completeLevelBtn.setEnabled(true);
		if (LevelController.totalRetryTimes == 0)
			totalRetryBtn.setEnabled(false);
		else
			totalRetryBtn.setEnabled(true);
		LevelController.writeRecord();
	}

	public void showStatus(JComponent c, int s)
	{
		String btnName = ((JButton) c).getName();
		if (btnName.equals("completeLevelBtn"))
			completeLevelBtnStatus = s;
		else if (btnName.equals("totalRetryBtn"))
			totalRetryBtnStatus = s;
		else if (btnName.equals("menuBtn"))
			menuBtnStatus = s;
		if ((completeLevelBtnStatus == 0 || completeLevelBtnStatus == 3)
				&& (totalRetryBtnStatus == 0 || totalRetryBtnStatus == 3) && menuBtnStatus == 0)
			statusBar.setText("");
		if (completeLevelBtnStatus != 0 && completeLevelBtnStatus != 3)
			// Button disable exception
			statusBar.setText("Restart the records of completed levels.");
		if (totalRetryBtnStatus != 0 && totalRetryBtnStatus != 3)
			// Button disable exception
			statusBar.setText("Clear the records of the times of retrying.");
		if (menuBtnStatus != 0)
			statusBar.setText("Back to main menu.");
	}

	public void LogoSpeak()
	{
		AudioPlayer logoSpeak = new AudioPlayer("sound/ReMusic.wav");
		logoSpeak.musicPlay();
	}
}
