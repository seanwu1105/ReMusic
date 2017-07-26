package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LevelController
{
	public static final int totalLevel = 16; // Starts from 1.
	public static int currentLevel = 1; // Starts from 1.
	public static int completedLevel = 1; // Starts from 1.
	public static int totalRetryTimes = 0; // Starts from 0.
	public static boolean gameCompleted = false; // Starts with false.

	public static final int BPM[] = { 128, 140, 130, 115, 130, 160, 125, 110, 140, 134, 87, 120, 22, 76, 130, 127 };
	// The numbers of channel in the level. The maximum number is 8.
	public static final int channelNumber[] = { 1, 1, 2, 2, 3, 3, 4, 4, 4, 4, 6, 7, 7, 8, 8, 8 };
	// The .mp3 source ID in each channel.
	public static final String soundPackID[][] = { { "Basic Kick" }, { "Basic Kick" }, { "Basic Kick", "Basic Snare" },
			{ "Basic Kick", "Basic Snare" }, { "Basic Kick", "Basic Snare", "Basic Clap" },
			{ "Basic Kick", "Basic Snare", "Basic Clap" }, { "Basic Kick", "Basic Snare", "Basic Clap", "Basic HiHat" },
			{ "Basic Kick", "Basic Snare", "Basic Clap", "Basic HiHat" },
			{ "Dubby-Kick", "Dubby-HiHat_1", "Dubby-HiHat_2", "Groover-Snare" },
			{ "Hop Box-Bass Kick", "Hop Box-HiHat", "Hop Box-Kick", "Hop Box-Snare" },
			{ "808 Kick", "Basic HiHat", "808 Snare", "707 Rim", "Thin Crash", "FPC Tamb" },
			{ "RD Tamb", "Grv OH_1", "Grv OH_2", "Grv Clav", "Grv Snareclap_1", "Grv SnareClap_2", "Grv Kick" },
			{ "Plucked!_D#", "Plucked!_C#", "Plucked!_A#", "Plucked!_F#", "Plucked!_F", "Reeverb Kick",
					"Reeverb Snare" },
			{ "FL Keys_C#6", "FL Keys_A", "FL Keys_G#", "FL Keys_F#", "FL Keys_C#5", "RD Snare", "DNC Kick",
					"RD HiHat" },
			{ "Digi_C8", "Digi_D#7", "Digi_C7", "Digi_D#6", "Digi_C6", "Digi_C5", "FPC Kick", "Grv Snareclap_3" },
			{ "DWM HiTom", "DWM OH", "DWM LowTom", "DWM CH_1", "DWM CH_2", "DWM MidTom", "DWM Snare", "DWM Kick" } };
	// {LEVEL{CHANNEL{ENABLE_KEY}}}
	public static final int answerKey[][][] = { { { 1, 5, 11, 13 } }, { { 1, 4, 7, 10, 13, 15 } },
			{ { 1, 5, 9 }, { 5, 13 } }, { { 1, 4, 7, 11, 14 }, { 5, 13 } },
			{ { 1, 5, 9, 13 }, { 3, 7, 11, 15 }, { 5, 13, 15 } }, { { 1, 11 }, { 8, 10 }, { 5, 13 } },
			{ { 1, 4, 7, 13, 15 }, { 5 }, { 9 }, { 1, 3, 5, 7, 9, 11, 13, 15 } },
			{ { 1, 3, 11, 12 }, { 8, 10, 16 }, { 5, 13 }, { 1, 3, 4, 5, 7, 8, 9, 11, 12, 13, 14, 15, 16 } },
			{ { 1, 5, 9, 13 }, { 1, 4, 7, 9, 12, 15 }, { 3, 7, 11, 15 }, { 5, 13 } },
			{ { 13 }, { 3, 4, 9 }, { 1, 7, 11 }, { 5, 10 } },
			{ { 1, 7, 11, 13 }, { 1, 3, 5, 7, 8, 9, 11, 13, 14, 15 }, { 4, 9 }, { 3, 15 }, { 1 }, { 9 } },
			{ { 4, 8, 12, 16 }, { 3, 7, 11, 15 }, { 2, 6, 10, 14 }, { 1, 5, 9, 13 }, { 5, 13 }, { 12, 14 },
					{ 1, 5, 9, 13, 15 } },
			{ { 5, 6, 7 }, { 8 }, { 4, 9, 10, 11, 12 }, { 1, 2, 3 }, { 13, 14, 15, 16 }, { 1, 3, 5, 7, 9, 11, 13, 15 },
					{ 2, 4, 6, 8, 10, 12, 14, 16 } },
			{ { 1 }, { 9 }, { 4, 8, 12, 15 }, { 3, 7, 11, 14, 16 }, { 2, 6, 10, 13 }, { 5, 13 },
					{ 1, 4, 7, 8, 10, 11, 14, 16 }, { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 } },
			{ { 6 }, { 15 }, { 5, 7, 9, 12, 14 }, { 16 }, { 2, 3, 8, 11, 13 }, { 1, 4, 10 }, { 1, 4, 7, 8, 11, 14 },
					{ 5, 13 } },
			{ { 3, 8 }, { 3, 7, 11, 15 }, { 10, 12 }, { 2, 3, 5, 6, 8, 9, 11, 12, 14, 15 }, { 1, 4, 7, 10, 13, 16 },
					{ 7 }, { 5, 13 }, { 1, 5, 9, 13 } } };

	public static void readRecord()
	{
		/*
		 * Save data format: completedLavel (integer), totalRetryTimes(integer),
		 * gameCompleted (boolean)
		 */
		File f = new File("savedata/savedata.dat");
		FileReader recordsFile;
		try
		{
			recordsFile = new FileReader(f);
			BufferedReader br = new BufferedReader(recordsFile);
			String tempData;
			try
			{
				tempData = br.readLine();
				completedLevel = Integer.parseInt(tempData);
				tempData = br.readLine();
				totalRetryTimes = Integer.parseInt(tempData);
				tempData = br.readLine();
				gameCompleted = Boolean.parseBoolean(tempData);
				br.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

		}
		catch (FileNotFoundException e) // Cannot find the save data
		{
			try
			{
				f.createNewFile();
				FileWriter newRecordsFile = new FileWriter(f, false);
				newRecordsFile.write("1" + System.getProperty("line.separator") + "0"
						+ System.getProperty("line.separator") + "false");
				newRecordsFile.flush();
				newRecordsFile.close();
				completedLevel = 1;
				totalRetryTimes = 0;
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	public static void writeRecord()
	{
		File f = new File("savedata/savedata.dat");
		try
		{
			FileWriter oldRecordsFile = new FileWriter(f, false);
			oldRecordsFile.write(String.valueOf(completedLevel) + System.getProperty("line.separator")
					+ String.valueOf(totalRetryTimes) + System.getProperty("line.separator")
					+ String.valueOf(gameCompleted));
			oldRecordsFile.flush();
			oldRecordsFile.close();
		}
		catch (IOException e)
		{
			FileWriter newRecordsFile;
			try
			{
				f.createNewFile();
				newRecordsFile = new FileWriter(f, false);

				newRecordsFile.write(String.valueOf(completedLevel) + System.getProperty("line.separator")
						+ String.valueOf(totalRetryTimes) + System.getProperty("line.separator")
						+ String.valueOf(gameCompleted));
				newRecordsFile.flush();
				newRecordsFile.close();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}