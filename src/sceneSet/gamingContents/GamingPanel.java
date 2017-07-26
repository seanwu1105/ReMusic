package sceneSet.gamingContents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import audio.AudioPlayer;
import audio.ButtonSoundFX;
import buttonStyle.GamingButtonStyle;
import controller.LevelController;
import controller.SceneController;
import initialization.DefaultSetting;
import initialization.Main;

@SuppressWarnings("serial")
public class GamingPanel extends JPanel implements ActionListener
{
	public final JLabel level = new JLabel();
	public final JLabel BPMnumber = new JLabel();
	public final JLabel retryNumber = new JLabel();

	private static ChannelRack sp; // Global variable for refresh level.
	private final JButton nextLevelBtn;

	private final int footerHeight = 40; // the height of footer
	private int beatTime = (int) (15000.0 / LevelController.BPM[0]);
	private int retryTimes;
	private final int semiquaver = DefaultSetting.semiquaver;
	// 15000 for the semiquaver of the music. Initialize the BPM as first level.
	// (BPM[0])

	/** Status bar variables and objects **/
	public final static JTextField statusBar = new JTextField();
	private static int playSampleBtnStatus = 0, playPatternBtnStatus = 0, restartBtnStatus = 0, menuBtnStatus = 0,
			nextLevelBtnStatus = 0;

	/** Rhythm blocks variables and objects **/
	private final JButton playPatternBtn;
	private final JPanel[] rhythmPosition = new JPanel[DefaultSetting.semiquaver];
	private Thread rhythmBlocksT;
	private boolean rhythmBlocksTRunFlag = false;

	/** Sample blocks variables and objects **/
	final JButton playSampleBtn;
	private AudioPlayer sampleMusic;
	private Thread sampleBlocksT;
	private boolean sampleTRunFlag = false;
	private boolean firstTimeListen;

	/** Answer check variables and objects **/
	private boolean correct;
	private boolean[][] ansKeyMap;

	public GamingPanel()
	{
		final Dimension frameSize = DefaultSetting.size;
		final Dimension samplerSize = DefaultSetting.samplerSize;

		setLayout(new BorderLayout());
		setBackground(DefaultSetting.bgColor);
		setPreferredSize(frameSize);

		/** HEADER **/
		final JPanel header = new JPanel(new BorderLayout());
		header.setBackground(DefaultSetting.swatches3);
		add(header, BorderLayout.NORTH);

		final JPanel headerLeft = new JPanel(new GridLayout(1, 8));
		headerLeft.setOpaque(false);
		header.add(headerLeft, BorderLayout.WEST);
		level.setOpaque(true);
		level.setFont(DefaultSetting.h2);
		level.setHorizontalAlignment(SwingConstants.CENTER);
		level.setBackground(DefaultSetting.swatches1);
		level.setForeground(Color.WHITE);
		level.setText(String.valueOf(LevelController.currentLevel));
		headerLeft.add(level);
		playSampleBtn = new JButton();
		playSampleBtn.setName("playSampleBtn");
		playSampleBtn.setBackground(DefaultSetting.swatches4);
		playSampleBtn.setForeground(Color.white);
		playSampleBtn.setUI(new GamingButtonStyle()); // customize the button.
		playSampleBtn.addActionListener(this);
		playSampleBtn.setActionCommand("playSample");
		playSampleBtn.setIcon(new ImageIcon("img/playSampleIcon.png"));
		playSampleBtn.setHorizontalAlignment(SwingConstants.CENTER);
		headerLeft.add(playSampleBtn);
		playPatternBtn = new JButton();
		playPatternBtn.setName("playPatternBtn");
		playPatternBtn.setBackground(DefaultSetting.swatches4);
		playPatternBtn.setForeground(Color.white);
		playPatternBtn.setUI(new GamingButtonStyle()); // customize the button.
		playPatternBtn.addActionListener(this);
		playPatternBtn.setActionCommand("playPattern");
		playPatternBtn.setIcon(new ImageIcon("img/playPatternIcon.png"));
		playPatternBtn.setHorizontalAlignment(SwingConstants.CENTER);
		headerLeft.add(playPatternBtn);
		final JButton restartBtn = new JButton();
		restartBtn.setName("restartBtn");
		restartBtn.addChangeListener(new ButtonSoundFX());
		restartBtn.setBackground(DefaultSetting.swatches4);
		restartBtn.setForeground(Color.white);
		restartBtn.setUI(new GamingButtonStyle()); // customize the button.
		restartBtn.addActionListener(this);
		restartBtn.setActionCommand("restart");
		restartBtn.setIcon(new ImageIcon("img/restartIcon.png"));
		restartBtn.setHorizontalAlignment(SwingConstants.CENTER);
		headerLeft.add(restartBtn);
		final JButton menuBtn = new JButton();
		menuBtn.setName("menuBtn");
		menuBtn.addChangeListener(new ButtonSoundFX());
		menuBtn.setBackground(DefaultSetting.swatches4);
		menuBtn.setForeground(Color.white);
		menuBtn.setUI(new GamingButtonStyle()); // customize the button.
		menuBtn.addActionListener(new SceneController());
		menuBtn.addActionListener(this);
		menuBtn.setActionCommand("Gaming -> Menu");
		menuBtn.setIcon(new ImageIcon("img/menuIcon.png"));
		menuBtn.setHorizontalAlignment(SwingConstants.CENTER);
		headerLeft.add(menuBtn);
		nextLevelBtn = new JButton();
		nextLevelBtn.setName("nextLevelBtn");
		nextLevelBtn.addChangeListener(new ButtonSoundFX());
		nextLevelBtn.setBackground(DefaultSetting.swatches4);
		nextLevelBtn.setForeground(Color.white);
		nextLevelBtn.setUI(new GamingButtonStyle()); // customize the button.
		nextLevelBtn.addActionListener(new SceneController());
		nextLevelBtn.addActionListener(this);
		nextLevelBtn.setActionCommand("nextLevel");
		nextLevelBtn.setIcon(new ImageIcon("img/nextLevelIcon.png"));
		nextLevelBtn.setHorizontalAlignment(SwingConstants.CENTER);
		headerLeft.add(nextLevelBtn);

		final JPanel BPMdisplay = new JPanel(new GridLayout(2, 1));
		BPMdisplay.setBackground(header.getBackground());
		final JLabel BPMtext = new JLabel("BPM");
		BPMtext.setHorizontalAlignment(SwingConstants.CENTER);
		BPMtext.setForeground(Color.WHITE);
		BPMtext.setFont(DefaultSetting.h3);
		BPMtext.setBorder(new EmptyBorder(5, 0, 0, 0));
		BPMdisplay.add(BPMtext);
		BPMnumber.setBorder(new LineBorder(header.getBackground(), 2));
		BPMnumber.setOpaque(true);
		BPMnumber.setFont(DefaultSetting.h3);
		BPMnumber.setBackground(header.getBackground().brighter().brighter());
		BPMnumber.setHorizontalAlignment(SwingConstants.CENTER);
		BPMnumber.setText(String.valueOf(LevelController.BPM[LevelController.currentLevel - 1]));
		BPMdisplay.add(BPMnumber);
		headerLeft.add(BPMdisplay);

		final JPanel retryDisplay = new JPanel(new GridLayout(2, 1));
		retryDisplay.setBackground(header.getBackground());
		final JLabel retryText = new JLabel("Retry");
		retryText.setHorizontalAlignment(SwingConstants.CENTER);
		retryText.setForeground(Color.WHITE);
		retryText.setFont(DefaultSetting.h3);
		retryText.setBorder(new EmptyBorder(5, 0, 0, 0));
		retryDisplay.add(retryText);
		retryNumber.setBorder(new LineBorder(header.getBackground(), 2));
		retryNumber.setOpaque(true);
		retryNumber.setFont(DefaultSetting.h3);
		retryNumber.setBackground(header.getBackground().brighter().brighter());
		retryNumber.setHorizontalAlignment(SwingConstants.CENTER);
		retryDisplay.add(retryNumber);
		headerLeft.add(retryDisplay);

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

		/** CONTENTS **/
		sp = new ChannelRack();
		add(sp, BorderLayout.CENTER);

		/** FOOTER **/
		final JPanel footer = new JPanel(new BorderLayout());
		final JPanel footerLeftSpace = new JPanel();
		final JPanel footerRightSpace = new JPanel();
		final JPanel footerContents = new JPanel(new GridLayout(1, semiquaver, DefaultSetting.rhythmPatternGap, 0));
		footerLeftSpace.setPreferredSize(new Dimension(
				DefaultSetting.samplerSoundSourceSize.width + (frameSize.width - samplerSize.width) / 2, footerHeight));
		footerLeftSpace.setBackground(DefaultSetting.swatches3.brighter());
		footerRightSpace.setPreferredSize(new Dimension((frameSize.width - samplerSize.width) / 2, footerHeight));
		footerRightSpace.setBackground(DefaultSetting.swatches3.brighter());
		footerContents.setBorder(new EmptyBorder(10, 0, 20, 0));
		footerContents.setBackground(DefaultSetting.swatches3.brighter());
		footer.add(footerLeftSpace, BorderLayout.WEST);
		footer.add(footerRightSpace, BorderLayout.EAST);
		footer.add(footerContents, BorderLayout.CENTER);
		for (int i = 0; i < rhythmPosition.length; i++)
		{
			rhythmPosition[i] = new JPanel();
			rhythmPosition[i].setBackground(DefaultSetting.swatches3);
			footerContents.add(rhythmPosition[i]);
		}
		add(footer, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) // Handle the play/stop pattern
												// thread.
	{
		String actionCmd = e.getActionCommand();
		if (actionCmd.equals("playPattern"))
		{
			if (!rhythmBlocksTRunFlag)
			{
				checkAns();
				rhythmBlocksTRunFlag = true; // To stop the rhythm blocks thread
												// properly.
				/** Rhythm Blocks Animation Display **/
				rhythmBlocksT = new Thread()
				{
					@Override
					public void run() // !!! Critical !!!//
					{
						for (int i = 0; rhythmBlocksTRunFlag && i < DefaultSetting.loopUpperBound; i++)
						{
							if (i % semiquaver - 1 < 0)
							{
								rhythmPosition[semiquaver - 1].setBackground(DefaultSetting.swatches3);
								for (int j = 0; j < ChannelRack.samplerSet.length; j++)
									if (ChannelRack.samplerSet[j].pattern[semiquaver - 1].isSelected())
										ChannelRack.samplerSet[j].pattern[semiquaver - 1]
												.setForeground(DefaultSetting.swatches2);
									else
										ChannelRack.samplerSet[j].pattern[semiquaver - 1]
												.setBackground(DefaultSetting.swatches1);
								activePattern(i);
							}
							else
							{
								rhythmPosition[i % semiquaver - 1].setBackground(DefaultSetting.swatches3);
								for (int j = 0; j < ChannelRack.samplerSet.length; j++)
									if (ChannelRack.samplerSet[j].pattern[i % semiquaver - 1].isSelected())
										ChannelRack.samplerSet[j].pattern[i % semiquaver - 1]
												.setForeground(DefaultSetting.swatches2);
									else
										ChannelRack.samplerSet[j].pattern[i % semiquaver - 1]
												.setBackground(DefaultSetting.swatches1);
								activePattern(i);
							}
							try
							{
								sleep(beatTime);
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					}
				};
				rhythmBlocksT.start();
				playPatternBtn.setIcon(new ImageIcon("img/stopPatternIcon.png"));
			}
			else
				stopRhythmThread();
		}
		if (actionCmd.equals("playSample"))
		{
			if (!sampleTRunFlag)
			{
				if (firstTimeListen)
					firstTimeListen = false;
				else
				{
					retryTimes++;
					LevelController.totalRetryTimes++;
					retryNumber.setText(String.valueOf(retryTimes));
				}
				playSample();
				checkAns();
				sampleTRunFlag = true; // To stop the sample blocks thread
										// properly.
				/** Sample Blocks Animation Display **/
				sampleBlocksT = new Thread()
				{
					@Override
					public void run() // !!! Critical !!!//
					{
						for (int i = 0; sampleTRunFlag && i < DefaultSetting.semiquaver; i++)
						{
							if (i - 1 >= 0)
								rhythmPosition[i - 1].setBackground(DefaultSetting.swatches3);
							rhythmPosition[i].setBackground(DefaultSetting.swatches3.darker().darker());
							try
							{
								sleep(beatTime);
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
						rhythmPosition[semiquaver - 1].setBackground(DefaultSetting.swatches3);
						sampleTRunFlag = false;
						playSampleBtn.setIcon(new ImageIcon("img/playSampleIcon.png"));
					}
				};
				sampleBlocksT.start();
				playSampleBtn.setIcon(new ImageIcon("img/stopSampleIcon.png"));
			}
			else
			{
				stopSampleThread();
				if (sampleMusic != null)
					sampleMusic.musicStop();
			}
		}
		if (actionCmd.equals("Gaming -> Menu"))
		{
			if (sampleMusic != null && sampleMusic.clip.isRunning())
				sampleMusic.musicStop();
			stopRhythmThread();
			stopSampleThread();
			playPatternBtn.setIcon(new ImageIcon("img/playPatternIcon.png"));
		}
		if (actionCmd.equals("restart"))
			for (int i = 0; i < ChannelRack.samplerSet.length; i++)
				for (int j = 0; j < rhythmPosition.length; j++)
					ChannelRack.samplerSet[i].pattern[j].setSelected(false);
		if (actionCmd.equals("nextLevel"))
		{
			LevelController.currentLevel++;
			if (LevelController.currentLevel <= LevelController.totalLevel)
			{
				if (LevelController.currentLevel > LevelController.completedLevel)
				{
					LevelController.completedLevel = LevelController.currentLevel;
					Main.levelPanel.refreshCompletedLevel();
				}
				refreshLevelContents();
			}
			LevelController.writeRecord();
		}
	}

	private void checkAns()
	{
		correct = true;
		for (int i = 0; i < ChannelRack.samplerSet.length; i++)
			// sampler number
			for (int j = 0; j < ChannelRack.samplerSet[0].pattern.length; j++)
				// pattern number
				if (ChannelRack.samplerSet[i].pattern[j].isSelected() != ansKeyMap[i][j])
				{
					correct = false;
					break;
				}
		if (correct)
		{
			nextLevelBtn.setEnabled(true);
			AudioPlayer levelCompleted = new AudioPlayer("sound/levelCompleted.wav");
			levelCompleted.musicPlay();
		}
	}

	private void playSample()
	{
		if (rhythmBlocksTRunFlag)
		{
			stopRhythmThread();
			rhythmBlocksTRunFlag = false;
			playPatternBtn.setIcon(new ImageIcon("img/playPatternIcon.png"));
		}
		sampleMusic = new AudioPlayer("sound/sample musics/" + LevelController.currentLevel + ".wav");
		sampleMusic.musicPlay();
	}

	private void activePattern(int i)
	{
		AudioPlayer source;
		rhythmPosition[i % semiquaver].setBackground(DefaultSetting.swatches3.brighter().brighter());
		for (int j = 0; j < ChannelRack.samplerSet.length; j++)
		{
			if (ChannelRack.samplerSet[j].pattern[i % semiquaver].isSelected())
			{
				ChannelRack.samplerSet[j].pattern[i % semiquaver]
						.setForeground(DefaultSetting.swatches1.brighter().brighter());
				source = new AudioPlayer("sound/packs/" + ChannelRack.samplerSet[j].samplerName + ".wav");
				source.musicPlay();
			}
			else
				ChannelRack.samplerSet[j].pattern[i % semiquaver]
						.setBackground(DefaultSetting.swatches1.brighter().brighter());
		}
	}

	public void stopRhythmThread()
	{
		playPatternBtn.setIcon(new ImageIcon("img/playPatternIcon.png"));
		if (rhythmBlocksTRunFlag)
		{
			rhythmBlocksTRunFlag = false; // To stop the rhythm blocks thread
			for (int i = 0; i < ChannelRack.samplerSet.length; i++)
			{
				for (int j = 0; j < rhythmPosition.length; j++)
				{
					ChannelRack.samplerSet[i].pattern[j].setBackground(DefaultSetting.swatches1);
					ChannelRack.samplerSet[i].pattern[j].setForeground(DefaultSetting.swatches2);
					rhythmPosition[j].setBackground(DefaultSetting.swatches3);
				}
			}
		}
	}

	public void stopSampleThread()
	{
		playSampleBtn.setIcon(new ImageIcon("img/playSampleIcon.png"));
		if (sampleTRunFlag)
		{
			sampleTRunFlag = false;
			for (int i = 0; i < rhythmPosition.length; i++)
				rhythmPosition[i].setBackground(DefaultSetting.swatches3);
		}
	}

	public void showStatus(JComponent c, int s)
	{
		String btnName = ((JButton) c).getName();
		if (btnName.equals("playPatternBtn"))
			playPatternBtnStatus = s;
		else if (btnName.equals("restartBtn"))
			restartBtnStatus = s;
		else if (btnName.equals("menuBtn"))
			menuBtnStatus = s;
		else if (btnName.equals("playSampleBtn"))
			playSampleBtnStatus = s;
		else if (btnName.equals("nextLevelBtn"))
			nextLevelBtnStatus = s;
		if (playPatternBtnStatus == 0 && restartBtnStatus == 0 && menuBtnStatus == 0
				&& (nextLevelBtnStatus == 0 || nextLevelBtnStatus == 3))
			statusBar.setText("");
		if (playSampleBtnStatus != 0)
			statusBar.setText("Play the sample music.");
		if (playPatternBtnStatus != 0)
			statusBar.setText("Play the pattern and check the answer.");
		if (restartBtnStatus != 0)
			statusBar.setText("Deselect all the pattern.");
		if (menuBtnStatus != 0)
			statusBar.setText("Back to main menu.");
		if (nextLevelBtnStatus != 0 && nextLevelBtnStatus != 3)
			// Button disable exception
			statusBar.setText("Go to next level!");
		if (btnName.endsWith(" Sampler.") && s != 0)
			statusBar.setText(btnName);
	}

	public void refreshLevelContents()
	{
		/** stop every the previous music **/
		if (sampleMusic != null && sampleMusic.clip.isRunning())
			sampleMusic.musicStop();
		stopRhythmThread();
		stopSampleThread();

		/** refresh channel rack **/
		remove(sp);
		sp = new ChannelRack();
		add(sp, BorderLayout.CENTER);
		sp.revalidate();
		sp.repaint();

		/** initiate the variables */
		nextLevelBtn.setEnabled(false);
		firstTimeListen = true;
		ansKeyMap = new boolean[ChannelRack.samplerSet.length][ChannelRack.samplerSet[0].pattern.length];
		for (int i = 0; i < LevelController.answerKey[LevelController.currentLevel - 1].length; i++)
			for (int j = 0; j < LevelController.answerKey[LevelController.currentLevel - 1][i].length; j++)
				ansKeyMap[i][LevelController.answerKey[LevelController.currentLevel - 1][i][j] - 1] = true;

		/** gaming panel data refresh **/
		level.setText(String.valueOf(LevelController.currentLevel));
		BPMnumber.setText(String.valueOf(LevelController.BPM[LevelController.currentLevel - 1]));
		beatTime = (int) (15000.0 / LevelController.BPM[LevelController.currentLevel - 1]);
		retryTimes = 0;
		retryNumber.setText(String.valueOf(retryTimes));
	}
}