package com.alagris.src.android;

import java.io.IOException;

import com.alagris.src.specific.SwarmInterface;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;
//import com.swarmconnect.Swarm;
//import com.swarmconnect.SwarmAchievement;
//import com.swarmconnect.SwarmActiveUser.GotCloudDataCB;
//import com.swarmconnect.SwarmLeaderboard;

import android.widget.Toast;

public class AndroidSwarmImplementation implements SwarmInterface
{

	private GoogleApiClient googleApiClient;
	private final AndroidApplication activity;

	public AndroidSwarmImplementation( AndroidApplication activity)
	{
		this.activity = activity;
	}

	@Override
	public void showLeaderboards()
	{
		// Swarm.showLeaderboards();
	}

	@Override
	public void showLeaderboard(int MY_LEADERBOARD_ID)
	{
		// SwarmLeaderboard.showLeaderboard(MY_LEADERBOARD_ID);
	}

	@Override
	public void submitScore(int MY_LEADERBOARD_ID, float lastScore)
	{
		// SwarmLeaderboard.submitScore(MY_LEADERBOARD_ID, lastScore);
	}

	@Override
	public void unlock(String MY_ACHIEVEMENT_ID)
	{
		Games.Achievements.unlock(googleApiClient, MY_ACHIEVEMENT_ID);
		// SwarmAchievement.unlock(MY_ACHIEVEMENT_ID);
	}

	@Override
	public void showDashboard()
	{
		// will not be implemented in google play services
		// Swarm.showDashboard();
	}

	@Override
	public boolean isOnline()
	{
		return googleApiClient.isConnected();
		// return Swarm.isOnline();
	}

	@Override
	public boolean isLoggedIn()
	{
		return true;// in google play services it's always true
		// return Swarm.isLoggedIn();
	}

	@Override
	public void showLogin()
	{
		// in google play services there is no need for it
		// Swarm.showLogin();
	}

	private static final int REQUEST_ACHIEVEMENTS = 1241;// random

	@Override
	public void showAchievements()
	{
		activity.startActivityForResult(Games.Achievements.getAchievementsIntent(googleApiClient),
				REQUEST_ACHIEVEMENTS);

		// Swarm.showAchievements();
	}

	@Override
	public int getUserID()
	{
		return -1;// not necessary in google play services
		// return Swarm.user.userId;
	}

	@Override
	public void uploadDataToCloud(String key, byte[] data)
	{
		Snapshots.OpenSnapshotResult open = Games.Snapshots.open(googleApiClient, key, true).await();

		int attemptCount = 0;
		whileLoop: while (true)
		{
			if (attemptCount++ < 5)
			{
				switch (open.getStatus().getStatusCode()) {
					case GamesStatusCodes.STATUS_SNAPSHOT_CONFLICT:
						Snapshot snapshot = open.getSnapshot();
						Snapshot conflictSnapshot = open.getConflictingSnapshot();

						Snapshot resolvedSnapshot = snapshot.getMetadata().getLastModifiedTimestamp() < conflictSnapshot
								.getMetadata().getLastModifiedTimestamp() ? conflictSnapshot : snapshot;

						open = Games.Snapshots.resolveConflict(googleApiClient, open.getConflictId(), resolvedSnapshot)
								.await();
						break;
					case GamesStatusCodes.STATUS_OK:
						break whileLoop;
					case GamesStatusCodes.STATUS_SNAPSHOT_CONTENTS_UNAVAILABLE:
						showError("Data saving problem", false);
						break whileLoop;
				}
			}
			else
			{
				showError(
						"ERROR! Someone seems to be playing on the same account right now and it conficts with your data! Your progress cannot be saved!",
						true);
				return;
			}

		}

		Snapshot snapshot = open.getSnapshot();
		snapshot.getSnapshotContents().writeBytes(data);
		SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder().build();
		Snapshots.CommitSnapshotResult commit = Games.Snapshots
				.commitAndClose(googleApiClient, snapshot, metadataChange).await();
		if (!commit.getStatus().isSuccess())
		{
			showError("Could not save your data!", false);
		}
		// if (Swarm.isLoggedIn()) {
		// Swarm.user.saveCloudData(key,value);
		// }
	}

	@Override
	public void showError(String text, boolean showForLongTime)
	{
		Toast.makeText(activity.getContext(), text, showForLongTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
	}

	//////// THIS IS FOR SWARM/////////////
	// private class MyGotCloudDataCB extends GotCloudDataCB
	// {
	// private String receivedData = null;
	//
	// public void gotData(String data)
	// {
	//
	// receivedData = data;
	// }
	// };
	//
	// MyGotCloudDataCB callback = new MyGotCloudDataCB();

	@Override
	public byte[] downloadDataFromCloud(String key)
	{

		// Open the saved game using its name.
		Snapshots.OpenSnapshotResult result = Games.Snapshots.open(googleApiClient, key, true).await();

		// Check the result of the open operation
		if (result.getStatus().isSuccess())
		{
			Snapshot snapshot = result.getSnapshot();
			// Read the byte content of the saved game.
			try
			{
				return snapshot.getSnapshotContents().readFully();
			}
			catch (IOException e)
			{
				showError("Error! Could not read game data!", false);
			}
		}
		else
		{
			showError("Error! Could not load game data!", false);
		}

		return null;

		// if (Swarm.isLoggedIn())
		// {
		// Swarm.user.getCloudData(key, callback);
		// return callback.receivedData;
		// }
		// return null;
	}

	public GoogleApiClient getGoogleApiClient()
	{
		return googleApiClient;
	}

	public void setGoogleApiClient(GoogleApiClient googleApiClient)
	{
		this.googleApiClient = googleApiClient;
	}

}
