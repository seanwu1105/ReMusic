package sceneSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import audio.ButtonSoundFX;
import buttonStyle.GamingButtonStyle;
import buttonStyle.PatternButtonStyle;
import controller.SceneController;
import initialization.DefaultSetting;

@SuppressWarnings("serial")
public class TutPanel extends JPanel implements ActionListener
{
	private Thread t;
	private boolean flickerFlag;
	private final JToggleButton[][] pattern;
	private final JButton nextLevelBtn;

	public TutPanel()
	{
		setLayout(new BorderLayout());
		setBackground(DefaultSetting.bgColor);
		setBorder(new EmptyBorder(0, 0, 25, 0));
		setPreferredSize(DefaultSetting.size);

		/* HEADER */
		final JPanel header = new JPanel(new BorderLayout());
		final JPanel headerLeft = new JPanel(new BorderLayout());
		final JLabel title = new JLabel("Tutorial");
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
		menuBtn.addActionListener(this);
		menuBtn.setActionCommand("Tut -> Menu");
		menuBtn.setIcon(new ImageIcon("img/menuIcon.png"));
		menuBtn.setHorizontalAlignment(SwingConstants.CENTER);
		headerLeft.setBackground(DefaultSetting.bgColor);
		headerLeft.add(menuBtn, BorderLayout.EAST);
		headerLeft.add(title, BorderLayout.WEST);
		header.add(headerLeft, BorderLayout.WEST);
		header.setOpaque(false);
		add(header, BorderLayout.NORTH);

		/* CONTENTS */
		final Color textBg = DefaultSetting.bgColor.brighter();
		final JPanel tutorialContents = new JPanel(new BorderLayout());
		tutorialContents.setOpaque(false);
		tutorialContents.setBorder(new EmptyBorder(10, 20, 0, 20));
		final JPanel tutHeader = new JPanel(new GridLayout(1, 3));
		tutHeader.setBackground(textBg);
		tutHeader.setBorder(new EmptyBorder(20, 0, 0, 0));
		final JPanel tutPic = new JPanel(new GridLayout(1, 3));
		tutPic.setBackground(textBg);
		final JPanel tutText = new JPanel(new GridLayout(1, 3));
		tutText.setBackground(textBg);
		tutorialContents.add(tutHeader, BorderLayout.NORTH);
		tutorialContents.add(tutPic, BorderLayout.CENTER);
		tutorialContents.add(tutText, BorderLayout.SOUTH);
		final JLabel[] tutHeaderSet = new JLabel[3];
		final JPanel[] tutPicSet = new JPanel[tutHeaderSet.length];
		final JTextArea[] tutTextSet = new JTextArea[tutHeaderSet.length];
		for (int i = 0; i < tutHeaderSet.length; i++)
		{
			tutHeaderSet[i] = new JLabel();
			tutHeaderSet[i].setFont(DefaultSetting.h1);
			tutHeaderSet[i].setHorizontalAlignment(SwingConstants.CENTER);
			tutPicSet[i] = new JPanel();
			tutPicSet[i].setOpaque(false);
			tutPicSet[i].setBorder(new EmptyBorder(10, 10, 10, 10));
			tutTextSet[i] = new JTextArea();
			tutTextSet[i].setOpaque(false);
			tutTextSet[i].setFont(DefaultSetting.f2);
			tutTextSet[i].setMargin(new Insets(10, 10, 10, 10));
			tutTextSet[i].setEditable(false);
			tutTextSet[i].setWrapStyleWord(true);
			tutTextSet[i].setLineWrap(true);
		}
		for (int i = 0; i < tutHeaderSet.length; i++)
			tutHeader.add(tutHeaderSet[i]);
		for (int i = 0; i < tutPicSet.length; i++)
			tutPic.add(tutPicSet[i]);
		for (int i = 0; i < tutTextSet.length; i++)
			tutText.add(tutTextSet[i]);
		tutHeaderSet[0].setText("Listen");
		tutHeaderSet[1].setText("Fill the Pattern");
		tutHeaderSet[2].setText("Go to Next Level");

		final JButton playSampleBtn = new JButton();
		playSampleBtn.setName("playSampleBtn");
		playSampleBtn.setBackground(DefaultSetting.swatches4);
		playSampleBtn.setForeground(Color.white);
		playSampleBtn.setUI(new GamingButtonStyle()); // customize the button.
		playSampleBtn.setIcon(new ImageIcon("img/playSampleIcon.png"));
		playSampleBtn.setHorizontalAlignment(SwingConstants.CENTER);
		tutPicSet[0].setLayout(new BorderLayout());
		tutPicSet[0].add(playSampleBtn);

		pattern = new JToggleButton[5][6];
		for (int i = 0; i < pattern.length; i++)
			for (int j = 0; j < pattern[i].length; j++)
			{
				pattern[i][j] = new JToggleButton();
				pattern[i][j].setUI(new PatternButtonStyle());
				pattern[i][j].setBackground(DefaultSetting.swatches1);
				pattern[i][j].setForeground(DefaultSetting.swatches2);
				tutPicSet[1].add(pattern[i][j]);
			}
		pattern[0][0].setSelected(true);
		pattern[0][5].setSelected(true);
		pattern[1][1].setSelected(true);
		pattern[1][4].setSelected(true);
		pattern[2][0].setSelected(true);
		pattern[2][2].setSelected(true);
		pattern[2][3].setSelected(true);
		pattern[2][5].setSelected(true);
		pattern[3][1].setSelected(true);
		pattern[3][4].setSelected(true);
		pattern[4][0].setSelected(true);
		pattern[4][5].setSelected(true);
		tutPicSet[1].setLayout(new GridLayout(pattern.length, pattern[0].length));

		tutPicSet[2].setLayout(new GridLayout(2, 1));
		final JButton playPatternBtn = new JButton();
		playPatternBtn.setName("playPatternBtn");
		playPatternBtn.setBackground(DefaultSetting.swatches4);
		playPatternBtn.setForeground(Color.white);
		playPatternBtn.setUI(new GamingButtonStyle()); // customize the button.
		playPatternBtn.setIcon(new ImageIcon("img/playPatternIcon.png"));
		playPatternBtn.setHorizontalAlignment(SwingConstants.CENTER);
		tutPicSet[2].add(playPatternBtn);

		nextLevelBtn = new JButton();
		nextLevelBtn.setName("nextLevelBtn");
		nextLevelBtn.setBackground(DefaultSetting.swatches4);
		nextLevelBtn.setForeground(Color.white);
		nextLevelBtn.setUI(new GamingButtonStyle()); // customize the button.
		nextLevelBtn.setIcon(new ImageIcon("img/nextLevelIcon.png"));
		nextLevelBtn.setHorizontalAlignment(SwingConstants.CENTER);
		tutPicSet[2].add(nextLevelBtn);

		tutTextSet[0].setText(
				"Listen to the sample track, and memorize the rhythm as many as you can. Headset recommend while playing the game.");
		tutTextSet[1].setText(
				"According to your memory, fill the pattern in each sampler of channel rack with the same rhythm of sample track.");
		tutTextSet[2].setText(
				"Click the \"Play and Check Button\". If the rhythm of pattern your filled is corresponding to the sample track, you can move to next level!");

		add(tutorialContents, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	// Only to stop the flicker thread.
	{
		if (e.getActionCommand().equals("Tut -> Menu"))
		{
			flickerFlag = false;
		}
	}

	public void flickerBtn(boolean isPlay) // Only for the animation.
	{
		if (isPlay)
		{
			t = new Thread()
			{
				@Override
				public void run()
				{
					flickerFlag = true;
					boolean brightFlicker = false; // Switch color states.
					for (int i = 0; flickerFlag && i < DefaultSetting.loopUpperBound; i++)
					{
						if (i % pattern[0].length - 1 < 0)
						{
							for (int j = 0; j < pattern.length; j++)
								if (pattern[j][pattern[0].length - 1].isSelected())
									pattern[j][pattern[0].length - 1].setForeground(DefaultSetting.swatches2);
								else
									pattern[j][pattern[0].length - 1].setBackground(DefaultSetting.swatches1);
							activePattern(i);
						}
						else
						{
							for (int j = 0; j < pattern.length; j++)
								if (pattern[j][i % pattern[0].length - 1].isSelected())
									pattern[j][i % pattern[0].length - 1].setForeground(DefaultSetting.swatches2);
								else
									pattern[j][i % pattern[0].length - 1].setBackground(DefaultSetting.swatches1);
							activePattern(i);
						}
						if (brightFlicker)
							nextLevelBtn.setEnabled(true);
						else
							nextLevelBtn.setEnabled(false);
						try
						{
							sleep(DefaultSetting.tutBgmBeatTime * 2);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						brightFlicker = !brightFlicker;
					}
				}
			};
			t.start();
		}
		else
		{
			flickerFlag = false;
			for (int i = 0; i < pattern.length; i++)
				for (int j = 0; j < pattern[i].length; j++)
				{
					pattern[i][j].setForeground(DefaultSetting.swatches2);
					pattern[i][j].setBackground(DefaultSetting.swatches1);
				}
		}
	}

	private void activePattern(int i)
	{
		for (int j = 0; j < pattern.length; j++)
		{
			if (pattern[j][i % pattern[0].length].isSelected())
			{
				pattern[j][i % pattern[0].length].setForeground(DefaultSetting.swatches1.brighter().brighter());
			}
			else
				pattern[j][i % pattern[0].length].setBackground(DefaultSetting.swatches1.brighter().brighter());
		}
	}
}
