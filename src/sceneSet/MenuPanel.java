package sceneSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import audio.ButtonSoundFX;
import buttonStyle.MenuButtonStyle;
import controller.LevelController;
import controller.SceneController;
import initialization.DefaultSetting;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel
{
	private final JPanel leftSpace = new JPanel();
	private final JPanel rightSpace = new JPanel();

	/* The buttons to switch the scene */
	private final JButton[] goToBtn = new JButton[5];

	/* For the logo of the game */
	private final JLabel header;

	public MenuPanel()
	{
		setLayout(new BorderLayout());
		setBackground(DefaultSetting.bgColor);
		setBorder(new EmptyBorder(10, 10, 25, 10));
		setPreferredSize(DefaultSetting.size);

		/** HEADER **/
		header = new JLabel();
		header.setHorizontalAlignment(SwingConstants.CENTER);
		add(header, BorderLayout.NORTH);
		gameCompletedRefresh();

		/** CONTENT **/
		final Dimension buttonSetSize = new Dimension(400, DefaultSetting.size.height);
		final JPanel buttonSet = new JPanel(new GridLayout(5, 1, 0, 10));
		buttonSet.setOpaque(false);
		add(buttonSet, BorderLayout.CENTER);

		leftSpace.setPreferredSize(
				new Dimension((int) (DefaultSetting.size.width - buttonSetSize.width) / 2, buttonSetSize.height));
		leftSpace.setOpaque(false);
		add(leftSpace, BorderLayout.WEST);

		rightSpace.setPreferredSize(
				new Dimension((int) (DefaultSetting.size.width - buttonSetSize.width) / 2, buttonSetSize.height));
		rightSpace.setOpaque(false);
		add(rightSpace, BorderLayout.EAST);

		goToBtn[0] = new JButton("New Game");
		goToBtn[0].setActionCommand("-> Gaming");
		goToBtn[1] = new JButton("Continue");
		goToBtn[1].setActionCommand("-> Level");
		goToBtn[2] = new JButton("Tutorial");
		goToBtn[2].setActionCommand("-> Tut");
		goToBtn[3] = new JButton("Setting & About");
		goToBtn[3].setActionCommand("-> Setting");
		goToBtn[4] = new JButton("Exit");
		goToBtn[4].setActionCommand("-> exit");

		for (int i = 0; i < goToBtn.length; i++)
		{
			goToBtn[i].setBackground(DefaultSetting.swatches2);
			goToBtn[i].setForeground(Color.white);
			goToBtn[i].setUI(new MenuButtonStyle()); // customize the button
			goToBtn[i].setFont(DefaultSetting.h1);
			goToBtn[i].addActionListener(new SceneController());
			goToBtn[i].addChangeListener(new ButtonSoundFX());
			buttonSet.add(goToBtn[i]);
		}
	}

	public void gameCompletedRefresh()
	{
		if (!LevelController.gameCompleted)
			header.setIcon(new ImageIcon("img/logo.png"));
		else
			header.setIcon(new ImageIcon("img/completedLogo.png"));
	}
}