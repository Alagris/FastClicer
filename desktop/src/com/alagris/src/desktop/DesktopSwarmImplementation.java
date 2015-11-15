package com.alagris.src.desktop;

import com.alagris.src.specific.SwarmInterface;

public class DesktopSwarmImplementation implements SwarmInterface
{

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
	public void unlock(int MY_ACHIEVEMENT_ID)
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

}
