package com.alagris.src;

import com.alagris.src.specific.SwarmInterface;

public abstract class IosSwarmImplementation implements SwarmInterface
{

	@Override
	public void showLeaderboards()
	{
	}

	@Override
	public void submitScore(int MY_LEADERBOARD_ID, float lastScore)
	{
	}

	@Override
	public void showDashboard()
	{
	}

	@Override
	public void showLeaderboard(int MY_LEADERBOARD)
	{

	}

	@Override
	public boolean isOnline()
	{
		return false;
	}

	@Override
	public boolean isLoggedIn()
	{
		return false;
	}

	@Override
	public void showLogin()
	{

	}

	@Override
	public void showAchievements()
	{

	}

	@Override
	public int getUserID()
	{
		return 0;
	}

	@Override
	public void uploadDataToCloud(String key, byte[] data)
	{

	}

	@Override
	public void showError(String text, boolean showForLongTime)
	{

	}

	@Override
	public void unlock(String MY_ACHIEVEMENT_ID)
	{

	}

	@Override
	public byte[] downloadDataFromCloud(String key)
	{
		return null;
	}

}
