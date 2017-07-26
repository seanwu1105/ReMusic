package audio;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer
{
	// The music to play.
	private File music;
	public Clip clip;

	// Save the time for music pause/resume.
	long clipTime;

	public AudioPlayer(String filePath)
	{
		music = new File(filePath);
	}

	public void musicPlay()
	{
		try
		{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(music));
			clip.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void musicStop()
	{
		clip.stop();
	}

	public void musicPause()
	{
		clipTime = clip.getMicrosecondPosition();
		clip.stop();
	}

	public void musicResume()
	{
		clip.setMicrosecondPosition(clipTime);
		clip.start();
	}

	public void musicLoop()
	{
		try
		{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(music));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch (Exception e)
		{
		}
	}
}
