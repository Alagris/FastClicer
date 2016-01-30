package com.alagris.src.desktop;

import com.alagris.src.specific.SwarmInterface;

public class DesktopSwarmImplementation implements SwarmInterface
{

	private Object monitor = new Object();

	@Override
	public void showLeaderboards()
	{
		System.out.println("showLeaderboards");
	}

	@Override
	public void submitScore(String MY_LEADERBOARD_ID, long lastScore)
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
	public void showLeaderboard(String MY_LEADERBOARD)
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

	@Override
	public byte[] downloadDataFromCloud(String key)
	{
		System.out.println("Cloud download for key=" + key);
		return null;
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
	}

	@Override
	public void showError(final String text, boolean showForLongTime)
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				synchronized (monitor)
				{
					while (supressErrors)
						try
						{
							monitor.wait();
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
				}
				System.err.println("ERROR! " + text);
			}
		});
		t.start();

	}

	private boolean supressErrors = false;

	@Override
	public void shouldHoldErrorMessages(boolean supressThem)
	{
		supressErrors = supressThem;
		if (!supressThem) synchronized (monitor)
		{
			monitor.notifyAll();
		}
	}

}
