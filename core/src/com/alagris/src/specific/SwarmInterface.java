package com.alagris.src.specific;

public interface SwarmInterface
{
	void showLeaderboards();

	void submitScore(String MY_LEADERBOARD_ID, long lastScore);

	void unlock(String MY_ACHIEVEMENT_ID);

	@Deprecated
	void showDashboard();

	void showLeaderboard(String MY_LEADERBOARD);

	boolean isOnline();

	@Deprecated
	boolean isLoggedIn();

	@Deprecated
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
	@Deprecated
	int getUserID();

	void showError(String text, boolean showForLongTime);

	void shouldHoldErrorMessages(boolean supressThem);
}
