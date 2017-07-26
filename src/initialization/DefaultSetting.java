package initialization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class DefaultSetting
{
	public static final int semiquaver = 16;
	// 15000 for the semiquaver of the background music. (125 BPM)
	public static final int menuBgmBeatTime = (int) (15000.0 / 125);
	public static final int levelBgmBeatTime = (int) (15000.0 / 88);
	public static final int tutBgmBeatTime = (int) (15000.0 / 128);

	public static final Dimension size = new Dimension(1000, 500);
	public static final Dimension samplerSize = new Dimension(950, 40);
	public static final Dimension samplerSoundSourceSize = new Dimension(180, samplerSize.height);
	public static final Dimension rhythmPatternSize = new Dimension(40, samplerSize.height);

	public static int rhythmPatternGap = 10;

	public static int loopUpperBound = 10000000;

	public static final Color bgColor = new Color(0XD0CEAA);
	public static final Color swatches1 = new Color(0XCA5F76);
	public static final Color swatches2 = new Color(0XE4A8AA);
	public static final Color swatches3 = new Color(0X848C91);
	public static final Color swatches4 = new Color(0X4B74B5);

	public static Font h1;
	public static Font h2;
	public static Font h3;
	public static Font f1;
	public static Font f2;

	public static void registerCustomFont()
	{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// String[] fontnames = ge.getAvailableFontFamilyNames();
		// System.out.println("\nFonts available on this platform: ");
		// for (int i = 0; i < fontnames.length; i++)
		// System.out.println(fontnames[i]);
		try
		{
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/OCRAStd.otf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/bitwise.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/SourceSansPro-Regular.otf")));
		} catch (FontFormatException | IOException e)
		{
			e.printStackTrace();
		}
		h1 = new Font("OCR A Std", Font.BOLD, 25);
		h2 = new Font("Bitwise", Font.BOLD, 30);
		h3 = new Font("OCR A Std", Font.PLAIN, 16);
		f1 = new Font("OCR A Std", Font.PLAIN, 20);
		f2 = new Font("Source Sans Pro", Font.PLAIN, 20);
	}
}
