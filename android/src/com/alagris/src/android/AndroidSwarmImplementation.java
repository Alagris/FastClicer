package com.alagris.src.android;

import com.alagris.src.specific.SwarmInterface;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmLeaderboard;

public class AndroidSwarmImplementation implements SwarmInterface
{

	@Override
	public void showLeaderboards()
	{
		Swarm.showLeaderboards();
	}

	@Override
	public void showLeaderboard(int MY_LEADERBOARD_ID)
	{
		SwarmLeaderboard.showLeaderboard(MY_LEADERBOARD_ID);
	}

	@Override
	public void submitScore(int MY_LEADERBOARD_ID, float lastScore)
	{
		SwarmLeaderboard.submitScore(MY_LEADERBOARD_ID, lastScore);
	}

	@Override
	public void unlock(int MY_ACHIEVEMENT_ID)
	{
		SwarmAchievement.unlock(MY_ACHIEVEMENT_ID);
	}

	@Override
	public void showDashboard()
	{
		Swarm.showDashboard();
	}

	@Override
	public boolean isOnline()
	{
		return Swarm.isOnline();
	}

	@Override
	public boolean isLoggedIn()
	{
		return Swarm.isLoggedIn();
	}

	@Override
	public void showLogin()
	{
		Swarm.showLogin();
	}

	@Override
	public void showAchievements()
	{
		Swarm.showAchievements();
	}

}
