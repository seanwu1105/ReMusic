package audio;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ButtonSoundFX implements ChangeListener
{
	AudioPlayer btnROFX = new AudioPlayer("sound/btnRolledOver.wav");

	@Override
	public void stateChanged(ChangeEvent e)
	{
		ButtonModel model = ((JButton) e.getSource()).getModel();
		if (model.isRollover())
		{
			btnROFX.musicPlay();
		}
	}

}
