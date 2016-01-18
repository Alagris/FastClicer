package com.alagris.src.specific;

public interface SwarmInterface
{
	void showLeaderboards();

	void submitScore(int MY_LEADERBOARD_ID, float lastScore);

	void unlock(String MY_ACHIEVEMENT_ID);

	void showDashboard();

	void showLeaderboard(int MY_LEADERBOARD);

	boolean isOnline();

	boolean isLoggedIn();

	void showLogin();

	void showAchievements();

	/**
	 * Returns null if there was connection problem. Empty string if the key has
	 * not been set yet. Key is used to identify the snapshot
	 */
	byte[] downloadDataFromCloud(String key);

	/** Key is used to identify the snapshot */
	void uploadDataToCloud(String key, byte[] data);

	/** when offline returns cached data from the last time user was online */
	int getUserID();

	void showError(String text, boolean showForLongTime);
}
