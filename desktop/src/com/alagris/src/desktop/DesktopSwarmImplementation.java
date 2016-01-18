package com.alagris.src.desktop;

import java.io.IOException;

import com.alagris.src.specific.SwarmInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class DesktopSwarmImplementation implements SwarmInterface
{

	private FileHandle settingsFile;

	@Override
	public void showLeaderboards()
	{
		System.out.println("showLeaderboards");
	}

	@Override
	public void submitScore(int MY_LEADERBOARD_ID, float lastScore)
	{
		System.out.println("submitScore " + MY_LEADERBOARD_ID);
	}

	@Override
	public void unlock(String MY_ACHIEVEMENT_ID)
	{
		System.out.println("unlock " + MY_ACHIEVEMENT_ID);
	}

	@Override
	public void showDashboard()
	{
		System.out.println("showDashboard");
	}

	@Override
	public void showLeaderboard(int MY_LEADERBOARD)
	{
		System.out.println("showLeaderboard " + MY_LEADERBOARD);
	}

	@Override
	public boolean isOnline()
	{
		System.out.println("isOnline");
		return false;
	}

	@Override
	public boolean isLoggedIn()
	{
		System.out.println("isLoggedIn");
		return false;
	}

	@Override
	public void showLogin()
	{
		System.out.println("showLogin");

	}

	@Override
	public void showAchievements()
	{
		System.out.println("showAchievements");
	}

	/**
	 * Returns true if file exists. False if doesn't exist (+automatically
	 * creates it or throws exception)
	 * 
	 * @throws IOException
	 */
	private boolean prepareSettingsFile() throws IOException
	{
		if (settingsFile == null)
		{
			settingsFile = Gdx.files.local("settings");
		}
		if (!settingsFile.exists())
		{
			settingsFile.file().createNewFile();
			return false;
		}
		return true;
	}

	@Override
	public byte[] downloadDataFromCloud(String key)
	{
		System.out.println("Cloud download for key=" + key);
		try
		{
			prepareSettingsFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return settingsFile.readBytes();
	}

	@Override
	public int getUserID()
	{
		System.out.println("Fetching user ID");
		return 0;
	}

	@Override
	public void uploadDataToCloud(String key, byte[] data)
	{
		System.out.println("Swarm cloud upload for key=" + key + " (value=" + new String(data) + ")");
		try
		{
			prepareSettingsFile();
			settingsFile.writeBytes(data, false);
		}
		catch (Exception e)
		{
		}
	}

	@Override
	public void showError(String text, boolean showForLongTime)
	{
		System.err.println("ERROR! " + text);
	}

}
