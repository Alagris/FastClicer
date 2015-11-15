package com.alagris.src.specific;

public interface SwarmInterface
{
	void showLeaderboards();

	void submitScore(int MY_LEADERBOARD_ID, float lastScore);

	void unlock(int MY_ACHIEVEMENT_ID);

	void showDashboard();

	void showLeaderboard(int MY_LEADERBOARD);

	boolean isOnline();

	boolean isLoggedIn();

	void showLogin();

	void showAchievements();
}
